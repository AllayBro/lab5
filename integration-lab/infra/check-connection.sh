#!/bin/bash
# Bash скрипт для проверки подключения к Terraform registry

echo "Проверка подключения к Terraform registry..."

# Проверка доступности registry
if curl -s --max-time 5 https://registry.terraform.io/.well-known/terraform.json > /dev/null 2>&1; then
    echo "✓ Подключение к registry.terraform.io успешно!"
else
    echo "✗ Не удалось подключиться к registry.terraform.io"
    echo ""
    echo "РЕШЕНИЕ: Используйте VPN или настройте прокси"
    echo "См. README.md для подробных инструкций"
fi

echo ""
echo "Проверка наличия kubeconfig..."

# Проверка kubeconfig
KUBECONFIG_PATH="$HOME/.kube/config"
if [ -f "$KUBECONFIG_PATH" ]; then
    echo "✓ kubeconfig найден: $KUBECONFIG_PATH"
else
    echo "✗ kubeconfig не найден: $KUBECONFIG_PATH"
    echo "  Убедитесь, что kubectl настроен правильно"
fi

echo ""
echo "Проверка Terraform..."

# Проверка Terraform
if command -v terraform &> /dev/null; then
    echo "✓ Terraform установлен:"
    terraform version
else
    echo "✗ Terraform не найден в PATH"
    echo "  Установите Terraform: https://www.terraform.io/downloads"
fi

