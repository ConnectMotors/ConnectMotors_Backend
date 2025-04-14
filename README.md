# 🚀 ConnectMotors Backend

Projeto de compra e venda de veículos (Versão de desenvolvimento)

Backend em Java (Spring Boot) com autenticação, cadastro de veículos, usuários com roles (ADMIN/USER) e integração com APIs externas (ex: CEP).

---

## 📋 Pré-requisitos
- Docker
- Git

---

## 🛠 Como Executar

### 1. Clone o repositório
```bash
git clone https://github.com/ConnectMotors/ConnectMotors_Backend.git
cd ConnectMotors_Backend
```

### 2. Inicie os containers com Docker Compose
```bash
docker-compose up --build
```
Isso irá subir o banco de dados e a aplicação Spring Boot automaticamente.

---

## 🔍 Acessando a Aplicação

- **Documentação da API (Swagger UI)**:  
  [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)  
  > Verifique os endpoints de Carro, Usuario, Autenticação, etc.

---

## 🌟 Funcionalidades Implementadas

- ✅ Autenticação JWT (Roles: ADMIN e USER)
- ✅ Cadastro de veículos (Marca, Modelo, Ano, Cor, Câmbio, etc.)
- ✅ Validação de CEP (Via API externa)
- ✅ Documentação automática (Swagger/OpenAPI)

---

## 📈 Fluxo da Aplicação
O fluxo da aplicação permite que usuários realizem ações como pesquisar veículos, criar anúncios e receber recomendações personalizadas. Abaixo está uma visão geral do processo:

Autenticação:
Usuários podem fazer login ou criar uma conta.
Ações Disponíveis:
Pesquisar Veículos: Escolha entre carros ou motos, selecione marca, modelo e atributos, e visualize anúncios.
Criar Anúncio: Adicione detalhes de um veículo (marca, modelo, atributos) para publicar um anúncio.
Recomendação Personalizada: A funcionalidade IAConnect analisa o perfil do usuário e recomenda um anúncio específico com base em suas preferências.
Interação:
Após visualizar anúncios (seja por pesquisa ou recomendação), usuários podem enviar mensagens diretamente ao dono do anúncio.
Para uma representação visual detalhada, consulte a imagem abaixo:

![Fluxo da Aplicação](Fluxo%20da%20aplicação.png)

---

## 📛 Parando a Aplicação
```bash
docker-compose down
```

---

## 💡 Dúvidas ou Problemas?

Verifique os logs do Docker:
```bash
docker-compose logs
```
