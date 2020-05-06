/*
 * Copyright 2018 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.palisade.clients.simpleclient;


import uk.gov.gchq.palisade.Generated;
import uk.gov.gchq.palisade.RequestId;
import uk.gov.gchq.palisade.data.serialise.Serialiser;
import uk.gov.gchq.palisade.resource.LeafResource;
import uk.gov.gchq.palisade.service.ConnectionDetail;
import uk.gov.gchq.palisade.service.request.DataRequestResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

//TODO REQUIRES REFACTORING TO DEPEND ON COMMON ELEMENTS
//TODO THE CLIENT SHOULD NOT DEPEND ON THE SERVICES SUCH AS PALISADE AND DATA SERVICE
//TODO INSTEAD THE RESTFUL INTERFACE SHOULD BE USED
//TODO we should be using feign annotation to do this


public class SimpleClient<T> {
    private final Serialiser<T> serialiser;

//    private final PalisadeService palisadeService;

//    public SimpleClient(final PalisadeService palisadeService, final Serialiser<T> serialiser) {
//        requireNonNull(palisadeService, "palisade service must be provided");
//        this.palisadeService = palisadeService;
//        requireNonNull(serialiser, "serialiser cannot be null");
//        this.serialiser = serialiser;
//    }

    public SimpleClient(final Serialiser<T> serialiser) {
        this.serialiser = requireNonNull(serialiser);
    }

    public DataRequestResponse makeRequest(final String fileName, final String resourceType, final String userId, final String purpose) {
//        final RegisterDataRequest dataRequest = new RegisterDataRequest().resourceId(fileName).userId(new UserId().id(userId)).context(new Context().purpose(purpose));
//        final DataRequestResponse dataRequestResponse = palisadeService.registerDataRequest(dataRequest).join();
//        return dataRequestResponse;
        return new DataRequestResponse();
    }

    public Stream<T> getObjectStreams(final DataRequestResponse response) {
        requireNonNull(response, "response");

        final List<CompletableFuture<Stream<T>>> futureResults = new ArrayList<>(response.getResources().size());
        for (final LeafResource leafResource : response.getResources()) {
            final ConnectionDetail connectionDetail = leafResource.getConnectionDetail();
//            final DataService dataService = connectionDetail.createService();
            final RequestId uuid = response.getOriginalRequestId();

//            final ReadRequest readRequest = new ReadRequest()
//                    .token(response.getToken())
//                    .resource(entry.getKey());
//            readRequest.setOriginalRequestId(uuid);
//
//            final CompletableFuture<ReadResponse> futureResponse = dataService.read(readRequest);
//            final CompletableFuture<Stream<T>> futureResult = futureResponse.thenApply(
//                    dataResponse -> {
//                        try {
//                            return getSerialiser().deserialise(dataResponse.asInputStream());
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//            );
//            futureResults.add(futureResult);
        }

        return futureResults.stream().flatMap(CompletableFuture::join);
    }

    public Stream<T> read(final String filename, final String resourceType, final String userId, final String purpose) {
        final DataRequestResponse dataRequestResponse = makeRequest(filename, resourceType, userId, purpose);
        return getObjectStreams(dataRequestResponse);
    }

    @Generated
    public Serialiser<T> getSerialiser() {
        return serialiser;
    }

    @Override
    @Generated
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SimpleClient)) {
            return false;
        }
        final SimpleClient<?> that = (SimpleClient<?>) o;
        return Objects.equals(serialiser, that.serialiser);
    }

    @Override
    @Generated
    public int hashCode() {
        return Objects.hash(serialiser);
    }

    @Override
    @Generated
    public String toString() {
        return new StringJoiner(", ", SimpleClient.class.getSimpleName() + "[", "]")
                .add("serialiser=" + serialiser)
                .toString();
    }
}
