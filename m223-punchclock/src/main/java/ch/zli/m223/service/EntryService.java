package ch.zli.m223.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zli.m223.model.Entry;

@ApplicationScoped
public class EntryService {
    @Inject
    private EntityManager entityManager;


    @Transactional
    public Entry createEntry(Entry entry) {
        entityManager.persist(entry);
        return entry;
    }

    @Transactional
    public void deleteEntry(Long id) {
        Entry enteros = null;

        for (Entry entry : findAll()) {
            if (entry.getId().equals(id)) {
                enteros = entry;
            }
        }
        if (enteros != null) {
            entityManager.remove(enteros);
        } else{
            throw new IllegalArgumentException("Entry with id " + id + " does not exist");
        }
        
    }

    @Transactional
    public Entry updateEntry(Long id, Entry entry) {
        entry.setId(id);
        return entityManager.merge(entry);
    }

    public List<Entry> findAll() {
        var query = entityManager.createQuery("FROM Entry", Entry.class);
        return query.getResultList();
    }
}
