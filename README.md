
# MéquiJunto

Plataforma de escuta, acolhimento e acompanhamento de colaboradores, desenvolvida para o **Desafio GC do programa Geração Caldeira**, com foco no desafio proposto pela empresa **Kallopolli**.

## Sobre o projeto

O MéquiJunto busca facilitar o acolhimento dos colaboradores e apoiar o acompanhamento de situações que possam exigir atenção. A solução combina uma interface web, um backend em Java e uma integração com WhatsApp e inteligência artificial.

O projeto foi desenvolvido no contexto do Desafio GC, promovido pelo programa Geração Caldeira, para atender ao desafio da Kallopolli.

## Tecnologias

- **Backend:** Java 21 e Spring Boot
- **Banco de dados:** PostgreSQL
- **Frontend:** HTML, CSS e JavaScript
- **Automação e integração:** n8n e WhatsApp
- **Documentação da API:** Swagger/OpenAPI

## Estrutura do projeto

```text
MequiJunto/
├── back/       # API Spring Boot
└── front/      # Interface web
```

## Pré-requisitos

- Java 21
- PostgreSQL
- Maven
- Node.js ou Python para servir o frontend localmente

## Configuração

### 1. Banco de dados

Crie um banco PostgreSQL e tenha em mãos a URL de conexão, o usuário e a senha.

### 2. Variáveis de ambiente

Crie um arquivo `.env` dentro da pasta `back/`:

```env
DB_URL=jdbc:postgresql://localhost:5432/NOME_DO_BANCO
DB_USERNAME=USUARIO_DO_BANCO
DB_PASSWORD=SENHA_DO_BANCO
JWT_SECRET=CHAVE_SECRETA_FORTE
FRONTEND_URL=http://localhost:5500
WHATSAPP_API_KEY=CHAVE_FORTE_DA_INTEGRACAO
```

Substitua os valores de exemplo pelos dados do seu ambiente. **Não publique o arquivo `.env` nem compartilhe suas credenciais.**

## Como executar

### Backend

No terminal, entre na pasta `back/` e execute:

```bash
mvn spring-boot:run
```

Por padrão, a API será iniciada em:

```text
http://localhost:8080
```

### Frontend

Na raiz do projeto, execute:

```bash
npx --yes http-server ./front -p 5500
```

Depois, abra no navegador:

```text
http://localhost:5500
```

Se alterar a porta do frontend, atualize também a variável `FRONTEND_URL` no arquivo `.env` e reinicie o backend.

## Integração com n8n

O backend disponibiliza um endpoint para receber eventos da integração com o WhatsApp:

```http
POST /api/integracao/whatsapp/evento
```

A requisição deve incluir os cabeçalhos:

```http
Content-Type: application/json
X-API-KEY: SUA_CHAVE_DE_INTEGRACAO
```

A chave enviada no cabeçalho deve ser a mesma configurada em `WHATSAPP_API_KEY` no backend.

Exemplo de corpo da requisição:

```json
{
  "telefone": "51999999999",
  "tipo": "TEXTO",
  "conteudo": "Mensagem de teste",
  "respostaIa": "Olá! Esta é uma resposta de teste.",
  "nivelRisco": "BAIXO"
}
```

O telefone deve corresponder a um colaborador ativo cadastrado no sistema.

## Segurança

- Mantenha senhas, tokens e chaves de integração fora do repositório.
- Não exponha credenciais do banco de dados no frontend ou nos fluxos do n8n.
- Em produção, utilize HTTPS e configure variáveis de ambiente com valores seguros.

## Status do projeto

Em desenvolvimento.

## Autoria

Equipe MéquiJunto — Desafio GC, programa Geração Caldeira, para a empresa Kallopolli.

**Integrantes:** 
Humberto Barreto, Rayssa Ribeiro, Vicenzo Brum, Nicolas Kreling, Pedro Moreira
