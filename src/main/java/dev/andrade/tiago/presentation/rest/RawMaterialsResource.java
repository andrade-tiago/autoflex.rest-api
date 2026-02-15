package dev.andrade.tiago.presentation.rest;

import java.util.UUID;

import dev.andrade.tiago.application.usecases.CreateRawMaterial.CreateRawMaterialInput;
import dev.andrade.tiago.application.usecases.CreateRawMaterial.CreateRawMaterialUseCase;
import dev.andrade.tiago.presentation.rest.dto.CreateRawMaterialRequest;
import dev.andrade.tiago.presentation.rest.dto.CreateRawMaterialResponse;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/materials")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RawMaterialsResource {
  @Inject
  CreateRawMaterialUseCase createRawMaterialUseCase;

  @POST
  public Response create(@Valid CreateRawMaterialRequest request) {
    var input = new CreateRawMaterialInput(
      request.name(),
      request.stock()
    );
    UUID materialUUID = this.createRawMaterialUseCase.execute(input);

    var response = new CreateRawMaterialResponse(materialUUID);
    return Response.status(Response.Status.CREATED)
      .entity(response)
      .build();
  }
}
