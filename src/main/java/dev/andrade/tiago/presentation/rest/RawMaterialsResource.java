package dev.andrade.tiago.presentation.rest;

import java.util.UUID;

import dev.andrade.tiago.application.usecases.CreateRawMaterial.*;
import dev.andrade.tiago.application.usecases.DeleteRawMaterial.DeleteRawMaterialUseCase;
import dev.andrade.tiago.application.usecases.ListAllMaterials.ListAllMaterialsUseCase;
import dev.andrade.tiago.application.usecases.UpdateRawMaterial.*;
import dev.andrade.tiago.presentation.rest.dto.CreateRawMaterialRequest;
import dev.andrade.tiago.presentation.rest.dto.CreateRawMaterialResponse;
import dev.andrade.tiago.presentation.rest.dto.ListAllMaterialsResponse;
import dev.andrade.tiago.presentation.rest.dto.UpdateRawMaterialRequest;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/materials")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RawMaterialsResource {
  @Inject ListAllMaterialsUseCase listAllMaterialsUseCase;
  @Inject CreateRawMaterialUseCase createRawMaterialUseCase;
  @Inject UpdateRawMaterialUseCase updateRawMaterialUseCase;
  @Inject DeleteRawMaterialUseCase deleteRawMaterialUseCase;

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

  @GET
  public Response getAll() {
    var output = this.listAllMaterialsUseCase.execute();

    var response = new ListAllMaterialsResponse(output.data());
    return Response.ok(response).build();
  }

  @PATCH
  @Path("/{id}")
  public Response update(
    @PathParam("id") UUID id,
    UpdateRawMaterialRequest request
  ) {
    var input = new UpdateRawMaterialInput(
      id,
      request.name(),
      request.stock()
    );
    var output = this.updateRawMaterialUseCase.execute(input);

    return Response.ok(output).build();
  }

  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam("id") UUID materialId) {
    this.deleteRawMaterialUseCase.execute(materialId);

    return Response.noContent().build();
  }
}
