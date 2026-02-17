package dev.andrade.tiago.presentation.rest.Production;

import java.util.List;

import dev.andrade.tiago.application.usecases.SuggestProduction.SuggestProductionUseCase;
import dev.andrade.tiago.presentation.rest.Production.dto.SuggestProductionResponse;
import dev.andrade.tiago.presentation.rest.Production.dto.SuggestProductionResponseItem;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/production")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductionResource {
  @Inject
  SuggestProductionUseCase suggestProductionUseCase;

  @GET
  @Path("/suggest")
  public Response suggestProduction() {
    var output = this.suggestProductionUseCase.execute();
    
    List<SuggestProductionResponseItem> responseItems = output.products()
      .stream()
      .map(item -> new SuggestProductionResponseItem(
        item.productId(),
        item.productName(),
        item.quantity(),
        item.unitValue(),
        item.totalValue()
      ))
      .toList();

    var response = new SuggestProductionResponse(output.totalValue(), responseItems);
    return Response.ok(response).build();
  }
}
