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

package uk.gov.gchq.palisade.clients.catclient;

//TODO REQUIRES REFACTORING TO DEPEND ON COMMON ELEMENTS
//TODO THE CLIENT SHOULD NOT DEPEND ON THE SERVICES SUCH AS PALISADE AND DATA SERVICE
//TODO INSTEAD THE RESTFUL INTERFACE SHOULD BE USED
//TODO we should be using feign annotation to do this

@Deprecated
public class CatClient {
//
//    private final PalisadeService palisadeService;
//
//    public CatClient(final PalisadeService palisadeService) {
//        Objects.requireNonNull(palisadeService, "palisade service must be provided");
//        this.palisadeService = palisadeService;
//    }
//
//    public static void main(final String[] args) throws InterruptedException {
//
//        if (args.length == 3) {
//
//            String userId = args[0];
//            String resource = args[1];
//            String purpose = args[2];
//
//            PalisadeService palisade = ClientUtil.getPalisadeClientEntryPoint();
//
//            new CatClient(palisade).read(userId, resource, purpose);
//
//        } else {
//            System.out.printf("Usage: %s userId resource purpose\n\n", CatClient.class.getSimpleName());
//            System.out.println("userId\t\t the unique id of the user making this query");
//            System.out.println("resource\t the name of the resource being requested");
//            System.out.println("purpose\t\t purpose for accessing the resource");
//        }
//    }
//
//    protected void read(final String userId, final String resource, final String purpose) {
//        final RegisterDataRequest dataRequest = new RegisterDataRequest().resourceId(resource).userId(new UserId().id(userId)).context(new Context().purpose(purpose));
//        final DataRequestResponse dataRequestResponse = palisadeService.registerDataRequest(dataRequest).join();
//        for (final Entry<LeafResource, ConnectionDetail> entry : dataRequestResponse.getResources().entrySet()) {
//            final ConnectionDetail connectionDetail = entry.getValue();
//            final DataService dataService = connectionDetail.createService();
//
//            final ReadRequest readRequest = new ReadRequest()
//                    .token(dataRequestResponse.getToken())
//                    .resource(entry.getKey());
//            readRequest.setOriginalRequestId(dataRequestResponse.getOriginalRequestId());
//
//            final CompletableFuture<ReadResponse> futureResponse = dataService.read(readRequest);
//            final CompletableFuture<InputStream> futureResult = futureResponse.thenApply(readResponse -> {
//                try {
//                    return readResponse.asInputStream();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//            new BufferedReader(new InputStreamReader(futureResult.join())).lines().forEachOrdered(System.out::println);
//        }
//    }
}
