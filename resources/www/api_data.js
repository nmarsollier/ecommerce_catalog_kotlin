define({ "api": [
  {
    "type": "get",
    "url": "/v1/articles/:articleId",
    "title": "Buscar Artículo",
    "name": "Buscar_Artículo",
    "group": "Articulos",
    "success": {
      "examples": [
        {
          "title": "Respuesta",
          "content": "HTTP/1.1 200 OK\n{\n     \"_id\": \"{id de articulo}\"\n     \"name\": \"{nombre del articulo}\",\n     \"description\": \"{descripción del articulo}\",\n     \"image\": \"{id de imagen}\",\n     \"price\": {precio actual},\n     \"stock\": {stock actual}\n     \"updated\": {fecha ultima actualización}\n     \"created\": {fecha creación}\n     \"enabled\": {activo}\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/kotlin/rest/GetArticleId.kt",
    "groupTitle": "Articulos",
    "error": {
      "examples": [
        {
          "title": "400 Bad Request",
          "content": "HTTP/1.1 400 Bad Request\n{\n    \"path\" : \"{Nombre de la propiedad}\",\n    \"message\" : \"{Motivo del error}\"\n}",
          "type": "json"
        },
        {
          "title": "400 Bad Request",
          "content": "HTTP/1.1 400 Bad Request\n{\n    \"error\" : \"{Motivo del error}\"\n}",
          "type": "json"
        },
        {
          "title": "500 Server Error",
          "content": "HTTP/1.1 500 Server Error\n{\n    \"error\" : \"{Motivo del error}\"\n}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "post",
    "url": "/v1/articles/:articleId",
    "title": "Actualizar Artículo",
    "name": "Actualizar_Artículo",
    "group": "Artículos",
    "examples": [
      {
        "title": "Body",
        "content": "{\n     \"name\": \"{nombre del articulo}\",\n     \"description\": \"{descripción del articulo}\",\n     \"image\": \"{id de imagen}\",\n     \"price\": {precio actual},\n     \"stock\": {stock actual}\n}",
        "type": "json"
      },
      {
        "title": "Header Autorización",
        "content": "Authorization=bearer {token}",
        "type": "String"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "Respuesta",
          "content": "HTTP/1.1 200 OK\n{\n     \"_id\": \"{id de articulo}\"\n     \"name\": \"{nombre del articulo}\",\n     \"description\": \"{descripción del articulo}\",\n     \"image\": \"{id de imagen}\",\n     \"price\": {precio actual},\n     \"stock\": {stock actual}\n     \"updated\": {fecha ultima actualización}\n     \"created\": {fecha creación}\n     \"enabled\": {si esta activo}\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/kotlin/rest/PostArticlesId.kt",
    "groupTitle": "Artículos",
    "error": {
      "examples": [
        {
          "title": "401 Unauthorized",
          "content": "HTTP/1.1 401 Unauthorized",
          "type": "json"
        },
        {
          "title": "400 Bad Request",
          "content": "HTTP/1.1 400 Bad Request\n{\n    \"path\" : \"{Nombre de la propiedad}\",\n    \"message\" : \"{Motivo del error}\"\n}",
          "type": "json"
        },
        {
          "title": "400 Bad Request",
          "content": "HTTP/1.1 400 Bad Request\n{\n    \"error\" : \"{Motivo del error}\"\n}",
          "type": "json"
        },
        {
          "title": "500 Server Error",
          "content": "HTTP/1.1 500 Server Error\n{\n    \"error\" : \"{Motivo del error}\"\n}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "get",
    "url": "/v1/articles/search/:criteria",
    "title": "Buscar Artículo",
    "name": "Buscar_Artículo",
    "group": "Artículos",
    "description": "<p>Busca artículos por nombre o descripción</p>",
    "success": {
      "examples": [
        {
          "title": "Respuesta",
          "content": "HTTP/1.1 200 OK\n[\n     {\n         \"_id\": \"{id de articulo}\"\n         \"name\": \"{nombre del articulo}\",\n         \"description\": \"{descripción del articulo}\",\n         \"image\": \"{id de imagen}\",\n         \"price\": {precio actual},\n         \"stock\": {stock actual}\n         \"updated\": {fecha ultima actualización}\n         \"created\": {fecha creación}\n         \"enabled\": {activo}\n     },\n...\n]",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/kotlin/rest/GetArticlesSearchCriteria.kt",
    "groupTitle": "Artículos",
    "error": {
      "examples": [
        {
          "title": "400 Bad Request",
          "content": "HTTP/1.1 400 Bad Request\n{\n    \"path\" : \"{Nombre de la propiedad}\",\n    \"message\" : \"{Motivo del error}\"\n}",
          "type": "json"
        },
        {
          "title": "400 Bad Request",
          "content": "HTTP/1.1 400 Bad Request\n{\n    \"error\" : \"{Motivo del error}\"\n}",
          "type": "json"
        },
        {
          "title": "500 Server Error",
          "content": "HTTP/1.1 500 Server Error\n{\n    \"error\" : \"{Motivo del error}\"\n}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "post",
    "url": "/v1/articles/",
    "title": "Crear Artículo",
    "name": "Crear_Artículo",
    "group": "Artículos",
    "examples": [
      {
        "title": "Body",
        "content": "{\n    \"name\": \"{nombre del articulo}\",\n    \"description\": \"{descripción del articulo}\",\n    \"image\": \"{id de imagen}\",\n    \"price\": {precio actual},\n    \"stock\": {stock actual}\n}",
        "type": "json"
      },
      {
        "title": "Header Autorización",
        "content": "Authorization=bearer {token}",
        "type": "String"
      }
    ],
    "success": {
      "examples": [
        {
          "title": "Respuesta",
          "content": "HTTP/1.1 200 OK\n{\n     \"_id\": \"{id de articulo}\"\n     \"name\": \"{nombre del articulo}\",\n     \"description\": \"{descripción del articulo}\",\n     \"image\": \"{id de imagen}\",\n     \"price\": {precio actual},\n     \"stock\": {stock actual}\n     \"updated\": {fecha ultima actualización}\n     \"created\": {fecha creación}\n     \"enabled\": {si esta activo}\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/kotlin/rest/PostArticles.kt",
    "groupTitle": "Artículos",
    "error": {
      "examples": [
        {
          "title": "401 Unauthorized",
          "content": "HTTP/1.1 401 Unauthorized",
          "type": "json"
        },
        {
          "title": "400 Bad Request",
          "content": "HTTP/1.1 400 Bad Request\n{\n    \"path\" : \"{Nombre de la propiedad}\",\n    \"message\" : \"{Motivo del error}\"\n}",
          "type": "json"
        },
        {
          "title": "400 Bad Request",
          "content": "HTTP/1.1 400 Bad Request\n{\n    \"error\" : \"{Motivo del error}\"\n}",
          "type": "json"
        },
        {
          "title": "500 Server Error",
          "content": "HTTP/1.1 500 Server Error\n{\n    \"error\" : \"{Motivo del error}\"\n}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "delete",
    "url": "/articles/:articleId",
    "title": "Eliminar Artículo",
    "name": "Eliminar_Artículo",
    "group": "Artículos",
    "success": {
      "examples": [
        {
          "title": "200 Respuesta",
          "content": "HTTP/1.1 200 OK",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/kotlin/rest/DeleteArticleId.kt",
    "groupTitle": "Artículos",
    "examples": [
      {
        "title": "Header Autorización",
        "content": "Authorization=bearer {token}",
        "type": "String"
      }
    ],
    "error": {
      "examples": [
        {
          "title": "401 Unauthorized",
          "content": "HTTP/1.1 401 Unauthorized",
          "type": "json"
        },
        {
          "title": "400 Bad Request",
          "content": "HTTP/1.1 400 Bad Request\n{\n    \"path\" : \"{Nombre de la propiedad}\",\n    \"message\" : \"{Motivo del error}\"\n}",
          "type": "json"
        },
        {
          "title": "400 Bad Request",
          "content": "HTTP/1.1 400 Bad Request\n{\n    \"error\" : \"{Motivo del error}\"\n}",
          "type": "json"
        },
        {
          "title": "500 Server Error",
          "content": "HTTP/1.1 500 Server Error\n{\n    \"error\" : \"{Motivo del error}\"\n}",
          "type": "json"
        }
      ]
    }
  },
  {
    "type": "topic",
    "url": "order/order-placed",
    "title": "Orden Creada",
    "group": "RabbitMQ",
    "description": "<p>Consume de mensajes order-placed desde Order con el topic &quot;order_placed&quot;.</p>",
    "success": {
      "examples": [
        {
          "title": "Mensaje",
          "content": "{\n\"type\": \"order-placed\",\n\"message\" : {\n\"cartId\": \"{cartId}\",\n\"orderId\": \"{orderId}\"\n\"articles\": [{\n\"articleId\": \"{model.article id}\"\n\"quantity\" : {quantity}\n}, ...]\n}\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/kotlin/rabbit/ConsumeCatalogOrderPlaced.kt",
    "groupTitle": "RabbitMQ",
    "name": "TopicOrderOrderPlaced"
  },
  {
    "type": "direct",
    "url": "catalog/model.article-data",
    "title": "Validación de Artículos",
    "group": "RabbitMQ_GET",
    "description": "<p>Escucha de mensajes model.article-data desde cart. Valida artículos</p>",
    "examples": [
      {
        "title": "Mensaje",
        "content": "{\n     \"type\": \"model.article-exist\",\n     \"exchange\" : \"{Exchange name to reply}\"\n     \"queue\" : \"{Queue name to reply}\"\n     \"message\" : {\n             \"referenceId\": \"{redId}\",\n             \"articleId\": \"{articleId}\"\n     }",
        "type": "json"
      }
    ],
    "version": "0.0.0",
    "filename": "src/main/kotlin/rabbit/ConsumeCatalogArticleData.kt",
    "groupTitle": "RabbitMQ_GET",
    "name": "DirectCatalogModelArticleData"
  },
  {
    "type": "direct",
    "url": "catalog/model.article-exist",
    "title": "Validación de Artículos",
    "group": "RabbitMQ_GET",
    "description": "<p>Escucha de mensajes model.article-exist desde cart. Valida artículos</p>",
    "examples": [
      {
        "title": "Mensaje",
        "content": "{\n     \"type\": \"model.article-exist\",\n     \"exchange\" : \"{Exchange name to reply}\"\n     \"queue\" : \"{Queue name to reply}\"\n     \"message\" : {\n         \"referenceId\": \"{redId}\",\n         \"articleId\": \"{articleId}\",\n     }",
        "type": "json"
      }
    ],
    "version": "0.0.0",
    "filename": "src/main/kotlin/rabbit/ConsumeCatalogArticleExist.kt",
    "groupTitle": "RabbitMQ_GET",
    "name": "DirectCatalogModelArticleExist"
  },
  {
    "type": "fanout",
    "url": "auth/logout",
    "title": "Logout",
    "group": "RabbitMQ_GET",
    "description": "<p>Escucha de mensajes logout desde auth. Invalida sesiones en cache.</p>",
    "examples": [
      {
        "title": "Mensaje",
        "content": "{\n\"type\": \"model.article-exist\",\n\"message\" : \"tokenId\"\n}",
        "type": "json"
      }
    ],
    "version": "0.0.0",
    "filename": "src/main/kotlin/rabbit/ConsumeAuthLogout.kt",
    "groupTitle": "RabbitMQ_GET",
    "name": "FanoutAuthLogout"
  },
  {
    "type": "direct",
    "url": "cart/model.article-exist",
    "title": "Validación de Articulos",
    "group": "RabbitMQ_POST",
    "description": "<p>Enviá de mensajes model.article-data desde cart. Valida articulos</p>",
    "success": {
      "examples": [
        {
          "title": "Mensaje",
          "content": "{\n     \"type\": \"model.article-data\",\n     \"message\" : {\n         \"cartId\": \"{cartId}\",\n         \"articleId\": \"{articleId}\",\n         \"valid\": True|False,\n         \"stock\": {stock}\n         \"price\": {price}\n     }\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/kotlin/rabbit/EmitArticleData.kt",
    "groupTitle": "RabbitMQ_POST",
    "name": "DirectCartModelArticleExist"
  },
  {
    "type": "direct",
    "url": "cart/model.article-exist",
    "title": "Validación de Artículos",
    "group": "RabbitMQ_POST",
    "description": "<p>Enviá de mensajes model.article-exist desde cart. Valida artículos</p>",
    "success": {
      "examples": [
        {
          "title": "Mensaje",
          "content": "{\n     \"type\": \"model.article-exist\",\n     \"message\" : {\n         \"cartId\": \"{cartId}\",\n         \"articleId\": \"{articleId}\",\n         \"valid\": True|False\n     }\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/kotlin/rabbit/EmitArticleValidation.kt",
    "groupTitle": "RabbitMQ_POST",
    "name": "DirectCartModelArticleExist"
  }
] });
