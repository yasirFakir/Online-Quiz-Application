#!/bin/bash
echo "Initializing Local Environment..."

# Create local_env directory structure
mkdir -p local_env/jdk
mkdir -p local_env/db_data

# Create default .env if missing
if [ ! -f .env ]; then
    echo "Creating default .env file..."
    cat > .env <<EOF
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/quizdb
DB_USER=postgres
DB_PASSWORD=password

# App Settings
APP_ENV=local
EOF
else
    echo ".env file already exists."
fi

echo "✅ Local environment structure created in 'local_env/'."
echo "   - JDK placeholder: local_env/jdk"
echo "   - Database data: local_env/db_data"
