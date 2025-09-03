# 📚 Escolinha Raio de Sol — Sistema de Gestão Escolar  
> Projeto acadêmico desenvolvido por **Matheus Silva Rodrigues** — Discente da UEFS  
> Matéria: *MI - Algoritmos e Programação II*  
> Versão **BETA 2.0**

---

## 📝 Descrição
O **Escolinha Raio de Sol** é um sistema simples de gerenciamento escolar, implementado em **Java** com arquitetura **MVC + Service + Repository**.  

O sistema roda no terminal e permite o gerenciamento de **Alunos**, **Turmas**, **Professores** e **Responsáveis**, além do lançamento de **notas** e cálculo automático de **média ponderada** para aprovação.

---

## 🛠️ Funcionalidades

- **Responsável**
  - Cadastro, listagem, atualização, busca e exclusão
  - Se um responsável for removido, todos os alunos vinculados a ele também são excluídos

- **Aluno**
  - Cadastro (obrigatório estar vinculado a um responsável e a uma turma)
  - Atualização, busca, listagem e exclusão
  - Lançamento de **3 notas** com cálculo de média
  - Verificação automática se o aluno está **aprovado ou reprovado**

- **Turma**
  - Cadastro (não exige professor vinculado no momento da criação)
  - Vinculação de alunos e professor
  - Listagem mostrando alunos vinculados
  - Busca detalhada (exibe série, ano letivo, professor e lista de alunos)

- **Professor**
  - Cadastro (necessário informar turma obrigatoriamente)
  - Listagem e busca detalhada
  - Atualização e exclusão

- **Boletim**
  - Lançamento de notas
  - Cálculo automático de média (mínimo **7.0** para aprovação)

---

## 🏗️ Arquitetura

