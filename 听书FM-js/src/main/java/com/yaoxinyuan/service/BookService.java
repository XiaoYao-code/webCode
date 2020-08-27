package com.peixinchen.service;

import com.peixinchen.dao.BookDao;
import com.peixinchen.dao.SectionDao;
import com.peixinchen.model.Book;
import com.peixinchen.model.Section;
import com.peixinchen.model.User;

import java.sql.SQLException;
import java.util.List;

public class BookService {
    private BookDao bookDao;
    private SectionDao sectionDao;

    public BookService() {
        bookDao = new BookDao();
        sectionDao = new SectionDao();
    }

    public List<Book> list() throws SQLException {
        return bookDao.selectAll();
    }

    public Book post(String title, User user) throws SQLException {
        return bookDao.insert(user, title);
    }

    public Book get(int bid) throws SQLException {
        Book book = bookDao.selectByBid(bid);
        if (book == null) {
            return null;
        }
        book.sections = sectionDao.selectByBid(bid);

        return book;
    }

    public void addSection(int bid, String name) throws SQLException {
        sectionDao.insert(bid, name);
    }
}
