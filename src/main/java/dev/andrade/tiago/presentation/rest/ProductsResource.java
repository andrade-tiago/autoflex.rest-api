package dev.andrade.tiago.presentation.rest;

import java.util.UUID;

import dev.andrade.tiago.application.dto.ProductCompositionItemInput;
import dev.andrade.tiago.application.usecases.CreateProduct.CreateProductInput;
import dev.andrade.tiago.application.usecases.CreateProduct.CreateProductUseCase;
import dev.andrade.tiago.presentation.rest.dto.CreateProductRequest;
import dev.andrade.tiago.presentation.rest.dto.CreateProductResponse;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductsResource {
  @Inject
  CreateProductUseCase createProductUseCase;

  @POST
  public Response create(@Valid CreateProductRequest request) {
    Log.debug(request);

    var input = new CreateProductInput(
      request.name(),
      request.value(),
      request.composition()
        .stream()
        .map(item -> new ProductCompositionItemInput(
          item.rawMaterialId(),
          item.requiredQuantity()
        ))
        .toList()
    );
    UUID newProductUuid = this.createProductUseCase.execute(input);

    var response = new CreateProductResponse(newProductUuid);
    return Response.status(Response.Status.CREATED)
      .entity(response)
      .build();
  }
}
