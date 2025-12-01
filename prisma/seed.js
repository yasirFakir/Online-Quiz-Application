
const { PrismaClient } = require('@prisma/client');
const fs = require('fs');
const path = require('path');

const prisma = new PrismaClient();

async function main() {
  console.log('Starting data seeding...');

  // Seed Users (Students and Teachers)
  const seedUsers = async () => {
    const studentCsvPath = path.join(__dirname, '../src/main/resources/credential/student.csv');
    const teacherCsvPath = path.join(__dirname, '../src/main/resources/credential/teacher.csv');

    const students = fs.readFileSync(studentCsvPath, 'utf8').split('\n').filter(Boolean).map(line => {
      const [username, password, displayName, fullName, role] = line.split(',');
      return { username, password, displayName, fullName: fullName || displayName, role: role ? role.trim().toUpperCase() : 'STUDENT' };
    });

    const teachers = fs.readFileSync(teacherCsvPath, 'utf8').split('\n').filter(Boolean).map(line => {
      const [username, password, displayName, fullName] = line.split(',');
      return { username, password, displayName, fullName: fullName || displayName, role: 'TEACHER' };
    });

    for (const user of [...students, ...teachers]) {
      await prisma.user.upsert({
        where: { username: user.username },
        update: {},
        create: user,
      });
      console.log(`Upserted user: ${user.username}`);
    }
  };

  // Seed Courses
  const seedCourses = async () => {
    const courseCsvPath = path.join(__dirname, '../src/main/resources/Courses/all.csv');
    const courses = fs.readFileSync(courseCsvPath, 'utf8').split('\n').filter(Boolean).map(line => {
      const [nameWithTeacher, description] = line.split(/,(?=(?:(?:[^""]*\"){2})*[^\"]*$)/); // Split by comma outside quotes
      const name = nameWithTeacher.split('_by_')[0].replace(/"/g, '');
      const teacherUsername = nameWithTeacher.split('_by_')[1];
      return { name, description: description.replace(/"/g, ''), teacherUsername };
    });

    for (const courseData of courses) {
      const teacher = await prisma.user.findUnique({ where: { username: courseData.teacherUsername } });
      if (teacher) {
        await prisma.course.upsert({
          where: { name: courseData.name },
          update: {},
          create: {
            name: courseData.name,
            description: courseData.description,
            teacher: { connect: { id: teacher.id } },
          },
        });
        console.log(`Upserted course: ${courseData.name}`);
      } else {
        console.warn(`Teacher ${courseData.teacherUsername} not found for course ${courseData.name}`);
      }
    }
  };

  // Seed Quizzes and Questions
  const seedQuizzesAndQuestions = async () => {
    const coursesDir = path.join(__dirname, '../src/main/resources/Courses');
    const courseDirs = fs.readdirSync(coursesDir, { withFileTypes: true })
      .filter(dirent => dirent.isDirectory())
      .map(dirent => dirent.name);

    for (const courseDirName of courseDirs) {
      const courseName = courseDirName.split('_by_')[0];
      const course = await prisma.course.findUnique({ where: { name: courseName } });

      if (!course) {
        console.warn(`Course ${courseName} not found, skipping quizzes.`);
        continue;
      }

      const quizFiles = fs.readdirSync(path.join(coursesDir, courseDirName))
        .filter(file => file.endsWith('.csv'));

      for (const quizFile of quizFiles) {
        const quizFilePath = path.join(coursesDir, courseDirName, quizFile);
        const quizContent = fs.readFileSync(quizFilePath, 'utf8').split('\n').filter(Boolean);

        if (quizContent.length === 0) continue;

        const quizTitle = quizContent[0].replace(/"/g, '');
        const quiz = await prisma.quiz.upsert({
          where: { title: quizTitle }, // Assuming quiz titles are unique for simplicity
          update: {},
          create: {
            title: quizTitle,
            course: { connect: { id: course.id } },
          },
        });
        console.log(`Upserted quiz: ${quizTitle}`);

        for (let i = 1; i < quizContent.length; i++) {
          const [questionText, correctAnswer, ...options] = quizContent[i].split(/,(?=(?:(?:[^""]*\"){2})*[^\"]*$)/).map(s => s.replace(/"/g, '').trim());
          await prisma.question.create({
            data: {
              text: questionText,
              correctAnswer: correctAnswer,
              options: options.join(','), // Store options as a comma-separated string
              quiz: { connect: { id: quiz.id } },
            },
          });
          console.log(`Created question for quiz ${quizTitle}: ${questionText.substring(0, 30)}...`);
        }
      }
    }
  };

  // Seed Enrollments
  const seedEnrollments = async () => {
    const studentInfoDir = path.join(__dirname, '../src/main/resources/studentInfo');
    const studentInfoFiles = fs.readdirSync(studentInfoDir).filter(file => file.endsWith('.csv'));

    for (const studentInfoFile of studentInfoFiles) {
      const studentUsername = studentInfoFile.replace('.csv', '');
      const student = await prisma.user.findUnique({ where: { username: studentUsername } });

      if (!student) {
        console.warn(`Student ${studentUsername} not found, skipping enrollments.`);
        continue;
      }

      const studentInfoPath = path.join(studentInfoDir, studentInfoFile);
      const enrollments = fs.readFileSync(studentInfoPath, 'utf8').split('\n').filter(Boolean).map(line => {
        const [courseNameWithTeacher, description, score] = line.split(/,(?=(?:(?:[^""]*\"){2})*[^\"]*$)/);
        const courseName = courseNameWithTeacher.split('_by_')[0].replace(/"/g, '');
        return { courseName, score: parseInt(score) };
      });

      for (const enrollmentData of enrollments) {
        const course = await prisma.course.findUnique({ where: { name: enrollmentData.courseName } });
        if (course) {
          await prisma.enrollment.upsert({
            where: {
              studentId_courseId: {
                studentId: student.id,
                courseId: course.id,
              },
            },
            update: { score: enrollmentData.score },
            create: {
              student: { connect: { id: student.id } },
              course: { connect: { id: course.id } },
              score: enrollmentData.score,
            },
          });
          console.log(`Upserted enrollment for ${studentUsername} in ${enrollmentData.courseName}`);
        } else {
          console.warn(`Course ${enrollmentData.courseName} not found for student ${studentUsername}`);
        }
      }
    }
  };

  // Seed Leaderboard Entries
  const seedLeaderboard = async () => {
    const leaderboardPath = path.join(__dirname, '../src/main/resources/leaderboard/leader.txt');
    const leaderboardEntries = fs.readFileSync(leaderboardPath, 'utf8').split('\n').filter(Boolean).map(line => {
      const [score, username] = line.split(/,?\s+/); // Split by comma or space
      return { score: parseInt(score), username };
    });

    for (const entry of leaderboardEntries) {
      const user = await prisma.user.findUnique({ where: { username: entry.username } });
      if (user) {
        await prisma.leaderboardEntry.upsert({
          where: { id: user.id }, // Assuming one leaderboard entry per user for simplicity
          update: { score: entry.score },
          create: {
            score: entry.score,
            user: { connect: { id: user.id } },
          },
        });
        console.log(`Upserted leaderboard entry for ${entry.username}`);
      } else {
        console.warn(`User ${entry.username} not found for leaderboard entry.`);
      }
    }
  };


  await seedUsers();
  await seedCourses();
  await seedQuizzesAndQuestions();
  await seedEnrollments();
  await seedLeaderboard();

  console.log('Data seeding complete.');
}

main()
  .catch(e => {
    console.error(e);
    process.exit(1);
  })
  .finally(async () => {
    await prisma.$disconnect();
  });
