# PowerShell скрипт для проверки подключения к Terraform registry

Write-Host "Проверка подключения к Terraform registry..." -ForegroundColor Yellow

# Проверка доступности registry
try {
    $response = Invoke-WebRequest -Uri "https://registry.terraform.io/.well-known/terraform.json" -TimeoutSec 5 -UseBasicParsing
    Write-Host "✓ Подключение к registry.terraform.io успешно!" -ForegroundColor Green
    Write-Host "  Status: $($response.StatusCode)" -ForegroundColor Green
} catch {
    Write-Host "✗ Не удалось подключиться к registry.terraform.io" -ForegroundColor Red
    Write-Host "  Ошибка: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
    Write-Host "РЕШЕНИЕ: Используйте VPN или настройте прокси" -ForegroundColor Yellow
    Write-Host "См. README.md для подробных инструкций" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Проверка наличия kubeconfig..." -ForegroundColor Yellow

# Проверка kubeconfig
$kubeconfigPath = "$env:USERPROFILE\.kube\config"
if (Test-Path $kubeconfigPath) {
    Write-Host "✓ kubeconfig найден: $kubeconfigPath" -ForegroundColor Green
} else {
    Write-Host "✗ kubeconfig не найден: $kubeconfigPath" -ForegroundColor Red
    Write-Host "  Убедитесь, что kubectl настроен правильно" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Проверка Terraform..." -ForegroundColor Yellow

# Проверка Terraform
try {
    $terraformVersion = terraform version
    Write-Host "✓ Terraform установлен:" -ForegroundColor Green
    Write-Host $terraformVersion
} catch {
    Write-Host "✗ Terraform не найден в PATH" -ForegroundColor Red
    Write-Host "  Установите Terraform: https://www.terraform.io/downloads" -ForegroundColor Yellow
}

