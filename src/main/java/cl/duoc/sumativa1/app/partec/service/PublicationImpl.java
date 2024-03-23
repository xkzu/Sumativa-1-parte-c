package cl.duoc.sumativa1.app.partec.service;

import cl.duoc.sumativa1.app.partec.model.Publishing;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PublicationImpl implements Publication {
    @Override
    public List<Publishing> getPublications() {
        return getPublishingData();
    }

    @Override
    public Publishing getPublication(int id) {
        for (Publishing publishing: getPublishingData()) {
            if (id == publishing.getId()) {
                return publishing;
            }
        }
        return null;
    }

    @Override
    public Map<String, String> getAverage(int id) {
        Map<String, String> averageResponse = new HashMap<>();

        Publishing publishing = getPublication(id);

        averageResponse.put("id", String.valueOf(publishing.getId()));
        averageResponse.put("user", publishing.getUser());
        averageResponse.put("title", publishing.getTitle());
        averageResponse.put("content", publishing.getContent());
        averageResponse.put("comment", publishing.getComment().toString());
        averageResponse.put("qualifications", publishing.getQualifications().toString());
        averageResponse.put("average", String.valueOf(getAverageCalculate(publishing.getQualifications())));
        return averageResponse;
    }

    private Double getAverageCalculate(List<Double> qualifications) {
        double sum = 0.0;
        for (Double value: qualifications) {
            sum += value;
        }
        return sum / qualifications.size();
    }

    // USEMOS LUEGO BD PROFE :c
    private List<Publishing> getPublishingData() {

        List<Publishing> listPublishings = new ArrayList<>();

        List<Double> listN1 = new ArrayList<>();
        listN1.add(7.0);
        listN1.add(5.8);

        List<String> listC1 = new ArrayList<>();
        listC1.add("This article really captures the majesty of the Alps. The photography is stunning, and the tips" +
                " for travelers are incredibly helpful. Can't wait to plan my trip!");
        listC1.add("I felt like the article glossed over the environmental impact of tourism in such pristine " +
                "areas. It's important to discuss sustainability.");

        Publishing publishing = new Publishing(
                1,
                "Richi",
                "Exploring the Beauty of the Alps",
                "Join us on a breathtaking journey through the Alps. Discover hidden gems, from secluded " +
                        "villages to panoramic hiking trails, and learn why this stunning landscape captivates " +
                        "the heart of every traveler.",
                listC1,
                listN1
        );

        List<Double> listN2 = new ArrayList<>();
        listN2.add(6.5);
        listN2.add(4.0);

        List<String> listC2 = new ArrayList<>();
        listC2.add("Fantastic read! It’s refreshing to see an in-depth analysis of renewable energy sources and their " +
                "potential impact on our planet's future.");
        listC2.add("The article seems overly optimistic about the scalability of renewable energy. It " +
                "ignores the current challenges with storage and distribution.");

        Publishing publishing2 = new Publishing(
                2,
                "Pepe",
                "The Future of Renewable Energy: A Deep Dive",
                "This comprehensive analysis examines the evolution of renewable energy technologies " +
                        "and their potential to replace fossil fuels. Explore the latest advancements in solar, " +
                        "wind, and hydro power that are paving the way for a greener future.",
                listC2,
                listN2
        );

        List<Double> listN3 = new ArrayList<>();
        listN3.add(6.8);
        listN3.add(3.4);

        List<String> listC3 = new ArrayList<>();
        listC3.add("I've been intimidated by coding for years, but this guide made it seem approachable and even fun. " +
                "Thanks for breaking it down into manageable parts!");
        listC3.add("The guide is too basic and skips over some important concepts that beginners should be aware of. " +
                "It's a good start but needs more depth.");

        Publishing publishing3 = new Publishing(
                3,
                "Charly",
                "A Beginner's Guide to Coding",
                "Ever wanted to learn coding but didn't know where to start? Our beginner's guide " +
                        "introduces the fundamentals of programming, from understanding syntax to " +
                        "writing your first lines of code. Start your coding journey here!",
                listC3,
                listN3
        );

        listPublishings.add(publishing);
        listPublishings.add(publishing2);
        listPublishings.add(publishing3);

        return listPublishings;
    }
}
