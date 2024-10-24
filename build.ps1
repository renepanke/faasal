# Clear the screen
Clear-Host

# Start the build process
Write-Host "Starting the build process..." -ForegroundColor Green

# Clean the project
Write-Host "Cleaning the project..."
mvn clean
if ($LASTEXITCODE -ne 0) {
    Write-Host "Cleaning failed!" -ForegroundColor Red
    exit $LASTEXITCODE
}

# Build AWS Lambda function
Write-Host "Building AWS Lambda function..."
mvn package -Paws-lambda-function
if ($LASTEXITCODE -ne 0) {
    Write-Host "AWS Lambda build failed!" -ForegroundColor Red
    exit $LASTEXITCODE
}

# Build Azure function
Write-Host "Building Azure function..."
mvn package -Pazure-function
if ($LASTEXITCODE -ne 0) {
    Write-Host "Azure function build failed!" -ForegroundColor Red
    exit $LASTEXITCODE
}

# Build GCP function
Write-Host "Building GCP function..."
mvn package -Pgcp-function
if ($LASTEXITCODE -ne 0) {
    Write-Host "GCP function build failed!" -ForegroundColor Red
    exit $LASTEXITCODE
}

# If everything passed
Write-Host "Build process completed successfully!" -ForegroundColor Green
