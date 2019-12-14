package org.bfh.dms.api.service;

import org.apache.lucene.search.Query;
import org.bfh.dms.core.domain.Device;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    List<String> fields;

    private final EntityManager entityManager;

    private FullTextEntityManager fullTextEntityManager;

    @Autowired
    public SearchService(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.fields = initializeFieldsList();
    }

    @Transactional
    public List<Device> search(String keyword) {

        this.fullTextEntityManager
                = Search.getFullTextEntityManager(entityManager);
        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Device.class)
                .get();

        Query query = queryBuilder
                .keyword()
                .wildcard()
                .onFields(fields.toArray(new String[fields.size()]))
                .matching("*" + keyword + "*")
                .createQuery();

        FullTextQuery jpaQuery
                = fullTextEntityManager.createFullTextQuery(query, Device.class);

        List<Device> results = jpaQuery.getResultList();


        return results;
    }

    private List<String> initializeFieldsList() {
        List<String> initializedList = new ArrayList<>();
        initializedList.add("deviceName");
        initializedList.add("model");
        initializedList.add("deviceUser");
        initializedList.add("os");
        initializedList.add("build");
        initializedList.add("cpu");
        initializedList.add("memory");
        initializedList.add("hardDisk");
        initializedList.add("installedBiosVersion");
        initializedList.add("serialNumber");
        initializedList.add("previousUser1");
        initializedList.add("previousUser2");
        return initializedList;
    }
}
