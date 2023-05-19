package com.vault.fundsloaderapplication.mapping;

import com.vault.fundsloaderapplication.model.LoadRequest;
import com.vault.fundsloaderapplication.model.LoadResponse;
import com.vault.fundsloaderapplication.model.Operation;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-19T17:05:53+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (GraalVM Community)"
)
@Component
public class LoadMapperImpl implements LoadMapper {

    @Override
    public Operation toOperation(LoadRequest request) {
        if ( request == null ) {
            return null;
        }

        Operation.OperationBuilder operation = Operation.builder();

        operation.customerId( request.getCustomerId() );
        operation.id( request.getId() );
        operation.time( request.getTime() );

        operation.loadAmount( getLoadAmountInBigDecimal(request.getLoadAmount()) );

        return operation.build();
    }

    @Override
    public LoadResponse toLoadResponse(Operation operation) {
        if ( operation == null ) {
            return null;
        }

        LoadResponse.LoadResponseBuilder loadResponse = LoadResponse.builder();

        loadResponse.id( operation.getId() );
        loadResponse.customerId( operation.getCustomerId() );
        loadResponse.accepted( operation.isAccepted() );

        return loadResponse.build();
    }
}
