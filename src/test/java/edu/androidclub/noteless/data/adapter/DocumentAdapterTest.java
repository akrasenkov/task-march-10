package edu.androidclub.noteless.data.adapter;

import edu.androidclub.noteless.domain.Note;
import edu.androidclub.noteless.domain.User;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class DocumentAdapterTest {

    private final Document document;
    private final Object entity;
    private final Class<?> clazz;

    public DocumentAdapterTest(TestTriple testTriple) {
        this.entity = testTriple.getEntity();
        this.document = testTriple.getDocument();
        this.clazz = testTriple.getClazz();
    }

    @Test
    public void testFromDocument() {
        Object resultEntity = DocumentAdapter.fromDocument(clazz, document);
        assertEquals("Invalid converting result!", entity, resultEntity);
    }

    @Test
    public void testToDocument() {
        Document resultDocument = DocumentAdapter.toDocument(entity);
        assertEquals("Invalid converting result!", document, resultDocument);
    }

    @Parameterized.Parameters
    public static Collection<TestTriple> data() {
        return Arrays.asList(
                new TestTriple(
                        User.class,
                        new User(
                                "SOME-ID",
                                "SOME-TOKEN",
                                123L
                        ),
                        new Document()
                                .append("id", "SOME-ID")
                                .append("token", "SOME-TOKEN")
                                .append("timestamp", 123L)
                ),
                new TestTriple(
                        Note.class,
                        new Note(
                                "SOME-NOTE-ID",
                                "SOME-TEXT",
                                321L
                        ),
                        new Document()
                                .append("id", "SOME-NOTE-ID")
                                .append("text", "SOME-TEXT")
                                .append("timestamp", 321L)
                )
        );
    }

    public static class TestTriple {
        private final Class<?> clazz;
        private final Object entity;
        private final Document document;

        public TestTriple(Class<?> clazz, Object entity, Document document) {
            this.clazz = clazz;
            this.entity = entity;
            this.document = document;
        }

        public Class<?> getClazz() {
            return clazz;
        }

        public Object getEntity() {
            return entity;
        }

        public Document getDocument() {
            return document;
        }
    }
}
