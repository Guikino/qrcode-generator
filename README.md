# QR Code Generator API

Uma API simples desenvolvida em **Java** com **Spring Boot**, que gera QR Codes a partir de um texto enviado via requisição HTTP.

## ✨ Funcionalidades

- Geração de QR Codes em formato PNG
- API REST pronta para integração
- Resposta com imagem gerada dinamicamente
- Projeto leve e fácil de rodar

## 🚀 Tecnologias utilizadas

- Java 17+
- Spring Boot Starter Web
- Lombok
- ZXing Core

## 📦 Como rodar localmente

1. Clone o repositório:

   ```bash
   git clone https://github.com/Guikino/qrcode-generator.git
   cd qrcode-generator
Certifique-se de que você possui o Java 17+ e o Maven instalados.

## Execute o projeto:

bash

Copiar
./mvnw spring-boot:run
Ou rode a classe QrcodeGeneratorApplication.java pela sua IDE (IntelliJ, Eclipse, etc).

A API estará disponível em:

text

Copiar
http://localhost:8080
🛠️ Como usar a API
Endpoint para geração de QR Code
POST /generate

Body da Requisição
Envie um JSON com o texto a ser convertido em QR Code:

json

Copiar
{
"text": "https://seulink.com"
}
Exemplo usando curl
bash

Copiar
curl -X POST -H "Content-Type: application/json" \
-d '{"text":"https://exemplo.com"}' \
http://localhost:8080/generate --output qrcode.png
Este comando gera o QR Code e salva no arquivo qrcode.png.

Respostas
200 OK — QR Code gerado com sucesso (imagem PNG retornada)
400 Bad Request — Se o campo text estiver vazio ou não for enviado
## 📜 Requisitos
Java 17 ou superior
Maven 3.8+
Lombok instalado ou plugin ativo na IDE
📄 Licença
Este projeto está licenciado sob a licença MIT.

Feito com 💻 por Guikino