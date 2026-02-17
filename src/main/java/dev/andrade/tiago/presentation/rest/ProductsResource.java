package dev.andrade.tiago.presentation.rest;

import java.util.UUID;

import dev.andrade.tiago.application.dto.ProductCompositionItemInput;
import dev.andrade.tiago.application.usecases.CreateProduct.*;
import dev.andrade.tiago.application.usecases.DeleteProduct.DeleteProductUseCase;
import dev.andrade.tiago.application.usecases.ListAllProducts.ListAllProductsUseCase;
import dev.andrade.tiago.presentation.rest.dto.CreateProductRequest;
import dev.andrade.tiago.presentation.rest.dto.CreateProductResponse;
import dev.andrade.tiago.presentation.rest.dto.ListAllProductsResponse;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductsResource {
  @Inject CreateProductUseCase createProductUseCase;
  @Inject DeleteProductUseCase deleteProductUseCase;
  @Inject ListAllProductsUseCase listAllProductsUseCase;

  @POST
  public Response create(@Valid CreateProductRequest request) {
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

  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam("id") UUID productId) {
    this.deleteProductUseCase.execute(productId);
    return Response.noContent().build();
  }

  @GET
  public Response getAll() {
    var output = this.listAllProductsUseCase.execute();

    var response = new ListAllProductsResponse(output.data());
    return Response.ok(response).build();
  }
}
