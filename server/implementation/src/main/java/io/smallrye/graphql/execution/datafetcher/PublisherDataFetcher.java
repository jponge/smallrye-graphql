package io.smallrye.graphql.execution.datafetcher;

import java.util.List;

import org.reactivestreams.Publisher;

import graphql.schema.DataFetchingEnvironment;
import io.smallrye.graphql.schema.model.Operation;
import io.smallrye.graphql.schema.model.Type;
import io.smallrye.mutiny.Multi;

/**
 * Handle Stream calls with Publisher
 *
 * @param <K>
 * @param <T>
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
public class PublisherDataFetcher<K, T> extends AbstractStreamingDataFetcher<K, T> {

    public PublisherDataFetcher(Operation operation, Type type) {
        super(operation, type);
    }

    @Override
    protected Multi<?> handleUserMethodCall(DataFetchingEnvironment dfe, final Object[] transformedArguments)
            throws Exception {
        Publisher<?> publisher = operationInvoker.invoke(transformedArguments);
        return (Multi<?>) Multi.createFrom().publisher(publisher);
    }

    @Override
    protected Multi<List<T>> handleUserBatchLoad(DataFetchingEnvironment dfe, final Object[] arguments) throws Exception {
        Publisher<List<T>> publisher = operationInvoker.invoke(arguments);
        return (Multi<List<T>>) Multi.createFrom().publisher(publisher);
    }
}
