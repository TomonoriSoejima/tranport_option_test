import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.transport.DefaultTransportOptions;
import co.elastic.clients.transport.TransportOptions;
import org.elasticsearch.client.RestClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;

public class ElasticsearchWithTransportOptionsExample {

    public static void main(String[] args) throws Exception {
        // Example of using TransportOptionsExample to create transport options
        DefaultTransportOptions transportOptions = createTransportOptions();

        // Initialize the RestClient with custom headers from transportOptions
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200))
                .setDefaultHeaders(transportOptions.headers().stream()
                        .map(header -> new BasicHeader(header.getKey(), header.getValue()))
                        .toArray(BasicHeader[]::new))
                .build();

        // Create the transport with the Jackson mapper
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        // Create the Elasticsearch client
        ElasticsearchClient client = new ElasticsearchClient(transport);




        SearchResponse<Product> search = client.search(s -> s
                        .index("products")

                        .query(q -> q
                                .term(t -> t
                                        .field("name.keyword")
                                        .value(v -> v.stringValue("jo"))
                                )),
                Product.class);


        System.out.println(search.hits().total().value());
        for (Hit<Product> hit: search.hits().hits()) {

            System.out.println(hit.toString());
            System.out.println(hit.innerHits());

        }

        // Now you can use the client with the configured transport options for operations
    }

    private static DefaultTransportOptions createTransportOptions() {
        // Initialize the builder

        TransportOptions options = new DefaultTransportOptions.Builder()
                .addHeader("X-Options-Header", "options value")
                .setParameter("max_concurrent_shard_requests", "3")
                .build();
        // Configure the transport options


        // Build and return the TransportOptions instance
        return (DefaultTransportOptions) options;
    }
}
