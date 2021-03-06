package com.addressBook.servlets;

import com.addressBook.jsonData.AddressBookController;
import com.addressBook.jsonData.AddressBookException;
import com.addressBook.jsonData.AddressDTO;
import com.addressBook.jsonData.PersonDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/jsp/DeleteRecord")
public class DeleteRecord extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String phoneNo = req.getParameter("phoneNo");
        System.out.println("inside delet record.......");
        HttpSession session = req.getSession();
        String bookName = (String) session.getAttribute("addressBookName");
        PersonDTO personDetails = (PersonDTO) session.getAttribute("personOpened");
        AddressBookController bookController = new AddressBookController();
        try {
            bookController.loadAddressBook(bookName);
        } catch (AddressBookException ignored) { ignored.printStackTrace();   }
        bookController.delete(bookName, personDetails.phoneNumber);
        bookController.save(bookName);
        session.setAttribute("addressBook", bookController.displayBook(bookName));
        session.setAttribute("personOpened", null);
        ServletContext servletContext = this.getServletContext();
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/jsp/book.jsp");
        dispatcher.forward(req,resp);
    }
}
