package com.example.spring.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final BookRepository bookRepository;

    public AppRunner(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        logger.info(".... Fetching books");
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-4567 -->" + bookRepository.getByIsbn("isbn-4567"));
        logger.info("isbn-4356 -->" + bookRepository.getByIsbn("isbn-4356"));
        logger.info("isbn-632 -->" + bookRepository.getSmallIsbn("isbn-632"));
        logger.info("isbn-632 -->" + bookRepository.getSmallIsbn("isbn-632"));
        logger.info("isbn-632 -->" + bookRepository.getSmallIsbn("isbn-632"));
        logger.info("i6 -->" + bookRepository.getSmallIsbn("i6"));
        logger.info("i6 -->" + bookRepository.getSmallIsbn("i6"));
        logger.info("i6 -->" + bookRepository.getSmallIsbn("i6"));
        logger.info("isbn-12634 -->" + bookRepository.getByIsbn("isbn-12634"));
        logger.info("isbn-12234 -->" + bookRepository.getByIsbn("isbn-12234"));
        logger.info("isbn-1234 -->" +bookRepository.updateBook("isbn-1234", "Updated book"));
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-6324 -->" +bookRepository.updateBook("isbn-6324", "Updated book"));
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        logger.info("isbn-63242 -->" + bookRepository.getByIsbn("isbn-63242"));
        logger.info("Clearing Cache for isbn-6324");
        bookRepository.clearCacheByKey("isbn-6324");
        logger.info("isbn-6324 -->" + bookRepository.getByIsbn("isbn-6324"));
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
        logger.info("Clearing All Cache..");
        bookRepository.clearCache();
        logger.info("isbn-6324 -->" + bookRepository.getByIsbn("isbn-6324"));
        logger.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
    }

}
