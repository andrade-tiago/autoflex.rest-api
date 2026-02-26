# Autoflex API

Este repositório é destinado à camada back-end do desafio proposto no processo seletivo para desenvolvedor full stack na empresa Projedata.

## :label: Sobre o desafio

O desafio consiste na implementação de um sistema para uma indústria, sendo responsável pela gestão dos produtos fabricados e das matérias-primas necessárias para a fabricação.

### :pushpin: Funcionalidades

- CRUD de produtos (Products);
- CRUD de matérias-primas (RawMaterials);
- Sugestão de produção baseada nas matérias-primas disponíveis em estoque, com prioridade para os produtos de maior valor.

## :computer: Como executar

Para executar localmente, clone o repositório para a sua máquina, e então, execute no terminal o seguinte comando:

```bash
mvnw quarkus:dev
```

> [!IMPORTANT]
> Você precisará ter instalado em sua máquina a plataforma **Java**, devidamente configurada com a variável de ambiente *JAVA_PATH*, e a ferramenta **Maven**.

## :gem: Tecnologias e Patterns

O back-end foi construído sobre o framework **Quarkus**, utilizando **arquitetura limpa**. Seguindo o fluxo natural de desenvolvimento com a ferramenta, para a persistência dos dados, foi utilizado o **Hibernate ORM com Panache**, por meio dos padrões **Active Record** e **Repository** juntos, uma abordagem híbrida para obter o melhor de ambos os mundos.
