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
package uk.gov.gchq.palisade.clients.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.JobContext;

import uk.gov.gchq.palisade.service.request.DataRequestResponse;

import java.util.List;
import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Utility methods for the input formats.
 */
final class InputFormatUtils {

    private InputFormatUtils() {
    }

    /**
     * Takes a response from the Palisade service and creates a list of input splits. A response may contain many
     * resources, which we wish to split across multiple input splits. Using the provided iterator, this will
     * round-robin resources to input splits by grouping them and then making a list of those groups. New {@link
     * DataRequestResponse}s are created inside the input splits to contain the new distributions.
     *
     * @param response the initial response
     * @param indexIt  the iterator that provides the keys to group the individual resources
     * @return a list of input splits
     */
    static List<PalisadeInputSplit> toInputSplits(final DataRequestResponse response,
                                                  final PrimitiveIterator.OfInt indexIt) {
        Objects.requireNonNull(response);
        Objects.requireNonNull(indexIt);
        return response.getResources().stream()
                //group by the indexIt, which will round robin the resources to
                //different groupings
                .collect(Collectors.groupingBy(x -> indexIt.next(), Collectors.toSet()))
                //now take the values of that map (we don't care about the keys)
                .values()
                .stream()
                //make each map into an input split
                .map(resources -> new PalisadeInputSplit(response.getToken(), resources, response.getOriginalRequestId()))
                //reduce to a list
                .collect(Collectors.toList());
    }

    /**
     * Creates and stores a UUID for a job. If none was present or was invalid, then reset it to a new one.
     *
     * @param context the job to make a UUID for
     * @return the UUID created or found
     */
    static UUID fetchUUIDForJob(final JobContext context) {
        Configuration conf = context.getConfiguration();
        String existingUUID = conf.get(PalisadeInputFormat.UUID_KEY);
        UUID uuid;
        try {
            if (existingUUID != null) {
                uuid = UUID.fromString(existingUUID);
                return uuid;
            }
        } catch (IllegalArgumentException e) {
            //fall through if uuid was illegal
        }
        //create new and store in job
        uuid = UUID.randomUUID();
        conf.set(PalisadeInputFormat.UUID_KEY, uuid.toString());
        return uuid;
    }
}
