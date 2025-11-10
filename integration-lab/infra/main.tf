terraform {
  required_providers {
    kubernetes = {
      source  = "hashicorp/kubernetes"
      # Убираем строгую версию для упрощения
      # version = "~> 1.13.5"
    }
  }
}

provider "kubernetes" {
  config_path = "~/.kube/config"
}

resource "kubernetes_namespace" "lab5" {
  metadata {
    name = "lab5"
  }
}
