package com.quiz.backend.service;

import com.quiz.backend.entity.PasswordResetToken;
import com.quiz.backend.entity.User;
import com.quiz.backend.repository.PasswordResetTokenRepository;
import com.quiz.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
public class PasswordResetService {
    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public PasswordResetService(PasswordResetTokenRepository tokenRepository, 
                                UserRepository userRepository, 
                                EmailService emailService) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Transactional
    public void createPasswordResetToken(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Generate 6-digit code
            String code = String.format("%06d", new Random().nextInt(999999));
            
            PasswordResetToken resetToken = new PasswordResetToken(code, user);
            tokenRepository.save(resetToken);

            emailService.sendEmail(user.getEmail(), "Your Verification Code", 
                    "Your password reset verification code is: " + code + "\n\nThis code will expire in 1 hour.");
        }
    }

    public boolean verifyCode(String email, String code) {
        Optional<PasswordResetToken> tokenOptional = tokenRepository.findByToken(code);
        if (tokenOptional.isPresent() && !tokenOptional.get().isExpired()) {
            return tokenOptional.get().getUser().getEmail().equals(email);
        }
        return false;
    }

    @Transactional
    public boolean resetPassword(String email, String code, String newPassword) {
        Optional<PasswordResetToken> tokenOptional = tokenRepository.findByToken(code);
        if (tokenOptional.isPresent() && !tokenOptional.get().isExpired()) {
            User user = tokenOptional.get().getUser();
            if (user.getEmail().equals(email)) {
                user.setPassword(newPassword);
                userRepository.save(user);
                tokenRepository.delete(tokenOptional.get());
                return true;
            }
        }
        return false;
    }
}
