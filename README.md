# 🚗 Sistema de Gerenciamento de Vistorias Veiculares

![Java](https://img.shields.io/badge/Java-17-blue)
![Licença](https://img.shields.io/badge/Licença-MIT-green)

---

## 📑 Sumário

1. [Introdução](#introdução)  
2. [Objetivos](#objetivos)  
3. [Fundamentação Teórica](#fundamentação-teórica)  
4. [Metodologia](#metodologia)  
   - [Arquitetura do Sistema](#arquitetura-do-sistema)  
   - [Funcionalidades](#funcionalidades)  
5. [Resultados](#resultados)  
6. [Considerações Finais](#considerações-finais)  
7. [Agradecimentos](#agradecimentos)  
8. [Apêndice A — Estrutura dos Principais Arquivos](#apêndice-a-—-estrutura-dos-principais-arquivos)  
9. [Apêndice B — Diagrama Simplificado de Navegação](#apêndice-b-—-diagrama-simplificado-de-navegação)  

---

## Introdução

A necessidade de sistemas informatizados eficientes cresce em diversos setores, incluindo **vistorias veiculares**.  
Este projeto propõe uma solução completa para gerenciamento de clientes, veículos, agendamentos, vistorias e pagamentos, contemplando diferentes perfis de usuário: **vistoriador, gerente e cliente**.

---

## Objetivos

**Objetivo Geral:**  
Desenvolver um sistema robusto para o gerenciamento de vistorias veiculares.  

**Objetivos Específicos:**  
- Automatizar o processo de agendamento e realização das vistorias.  
- Acompanhar o status de vistorias e pagamentos em tempo real.  
- Facilitar a gestão de funcionários, clientes e veículos.  
- Gerar relatórios e dashboards customizáveis para apoiar decisões gerenciais.

---

## Fundamentação Teórica

O sistema utiliza o padrão **MVC (Model-View-Controller)** para separar responsabilidades, garantindo **manutenção e escalabilidade**.  
- **View:** Interface gráfica com Java Swing, amigável e responsiva.  
- **Model:** Representa entidades do negócio (Cliente, Veículo, Agendamento, etc.).  
- **Controller:** Centraliza regras de negócio.  
- **DAO (Data Access Object):** Abstrai o acesso a dados, permitindo integração com diferentes bancos.

---

## Metodologia

### Arquitetura do Sistema

O sistema segue uma **arquitetura em camadas (MVC):**  

- **Model:** Entidades de negócio  
- **DAO:** Persistência de dados  
- **Controller:** Regras de negócio  
- **View:** Interface gráfica com três dashboards:  
  - **DashboardVistoriador:** Agendamentos, vistorias e relatórios operacionais.  
  - **DashBoardGerente:** Gestão de funcionários e relatórios gerenciais.  
  - **DashBoardCliente:** Consulta de dados pessoais, agendamentos e pagamentos.

### Funcionalidades

**DashboardVistoriador**  
- Visualização de agendamentos pendentes e concluídos  
- Cadastro de clientes, veículos e agendamentos  
- Registro detalhado de vistorias  
- Relatórios filtráveis por data, cliente e funcionário  
- Exportação de relatórios em CSV  

**DashBoardGerente**  
- Gerenciamento de funcionários (cadastro, edição, exclusão)  
- Situação de funcionários (status)  
- Relatórios financeiros (faturamento, pagamentos)  

**DashBoardCliente**  
- Visualização e edição de dados pessoais  
- Consulta de agendamentos e status de pagamentos  
- Pagamento online de vistorias  
- Visualização de relatórios de vistorias realizadas

---

## Resultados

- **Gestão Centralizada:** Processos unificados  
- **Melhoria Operacional:** Redução de retrabalhos e erros humanos  
- **Usabilidade:** Interface adaptada a diferentes perfis  
- **Suporte à Decisão:** Relatórios customizáveis para decisões gerenciais

---

## Considerações Finais

O projeto atende aos requisitos funcionais primários de uma empresa de vistoria veicular.  
**Futuras expansões sugeridas:**  
- Notificações automáticas por e-mail/SMS  
- Controle de acesso refinado por níveis de permissão

---

## Agradecimentos

Agradecemos ao professor **Hudson Neves**, orientador deste projeto, pelo apoio técnico, pelas sugestões construtivas e pela orientação contínua ao longo do desenvolvimento do sistema.

---

## Apêndice A — Estrutura dos Principais Arquivos

| Arquivo | Descrição |
|---------|-----------|
| view/DashboardVistoriador.java | Interface para vistoriadores |
| view/DashBoardGerente.java | Interface para gerentes |
| view/DashBoardCliente.java | Interface para clientes |
| model/Cliente.java | Classe da entidade cliente |
| dao/ClienteDAO.java | Persistência de clientes |
| controller/AgendamentoController.java | Lógica de agendamento |

---


