# Terraform инфраструктура

Минимальная Terraform конфигурация для создания namespace `lab5` в Kubernetes.

## Установка

1. Установите Terraform: https://www.terraform.io/downloads

2. Если есть проблемы с доступом к registry.terraform.io:
   - Используйте VPN
   - Или настройте зеркало в `.terraformrc`

## Использование

```bash
cd integration-lab/infra

# Инициализация (загрузка провайдеров)
terraform init

# Просмотр плана изменений
terraform plan

# Применение изменений (создание namespace)
terraform apply
```

## Решение проблем с подключением к registry

Если получаете ошибку `could not connect to registry.terraform.io`:

### Вариант 1: Использовать VPN (РЕКОМЕНДУЕТСЯ)
Как указано в README.txt - Terraform работает исключительно через VPN.
1. Подключите VPN
2. Запустите `terraform init` снова

### Вариант 2: Использовать прокси
Если у вас есть HTTP прокси:
```bash
export HTTP_PROXY=http://proxy.example.com:8080
export HTTPS_PROXY=http://proxy.example.com:8080
terraform init
```

### Вариант 3: Использовать уже скачанный провайдер
Если провайдер уже был скачан ранее (например, с другого компьютера):
1. Скопируйте провайдер из `~/.terraform.d/plugins/` на этот компьютер
2. Terraform использует кеш автоматически

### Вариант 4: Ручная установка провайдера
1. Скачайте провайдер вручную (через VPN/другой компьютер):
   - https://github.com/hashicorp/terraform-provider-kubernetes/releases
2. Создайте директорию: `mkdir -p ~/.terraform.d/plugins/registry.terraform.io/hashicorp/kubernetes/<version>/<os>_<arch>/`
3. Поместите бинарный файл провайдера в эту директорию
4. Запустите `terraform init`

### Вариант 5: Использовать зеркало (если доступно)
Если доступно зеркало Terraform registry:
1. Настройте `.terraformrc` (раскомментируйте блок `provider_installation`)
2. Укажите URL зеркала

### Проверка подключения
Проверьте доступность registry:
```bash
curl https://registry.terraform.io/.well-known/terraform.json
```

Если команда не выполняется - нужен VPN или прокси.

## Структура

- `main.tf` - основная конфигурация Terraform
- `.terraformrc` - конфигурация Terraform CLI (опционально)

## Что создается

- Namespace `lab5` в Kubernetes кластере

