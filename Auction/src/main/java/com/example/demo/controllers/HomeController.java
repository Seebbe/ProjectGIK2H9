package com.example.demo.controllers;

import com.example.demo.models.Category;
import com.example.demo.models.Item;
import com.example.demo.models.User;
import com.example.demo.repositories.BidRepository;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ItemRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.timers.EndAuctionTimer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Controller

public class HomeController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    EndAuctionTimer endAuctionTimer;

    @EventListener(ApplicationReadyEvent.class)
    public void runOnStart() throws IOException {
        //Skapar upp användare
        User u1 = new User("Adminsson", "admin@admin.se", "$2y$12$6whe.3786xhMmCuwWJj.mevsUru0EbCBVoFZswRbGNkQ/A5sEU3EW", "Cool admin", "ROLE_ADMIN",1);
        User u2 = new User("Sebbe", "hassehasse300@gmail.com", "$2y$12$6whe.3786xhMmCuwWJj.mevsUru0EbCBVoFZswRbGNkQ/A5sEU3EW", "I do this for a living", "ROLE_USER",1);
        User u3 = new User("Marcus", "massus.hdu@gmail.com", "$2y$12$6whe.3786xhMmCuwWJj.mevsUru0EbCBVoFZswRbGNkQ/A5sEU3EW", "Like to buy stuff", "ROLE_USER",1);
        User u4 = new User("Kevin", "mailtest023123@gmail.com", "$2y$12$6whe.3786xhMmCuwWJj.mevsUru0EbCBVoFZswRbGNkQ/A5sEU3EW", "A student who likes to buy stuff", "ROLE_USER",1);
        userRepository.save(u1);
        userRepository.save(u4);
        userRepository.save(u3);
        userRepository.save(u2);
        Category c1 = new Category("Electronic", "Everything about electronics");
        Category c2 = new Category("Cars", "Everything about cars");
        Category c3 = new Category("House", "Everything to your house ");
        Category c4 = new Category("Hobbie", "All hobbies");
        Category c5 = new Category("Sports", "You must stay fit brow");
        categoryRepository.save(c1);
        categoryRepository.save(c2);
        categoryRepository.save(c3);
        categoryRepository.save(c4);
        categoryRepository.save(c5);
        //PASSWORD = 123
        //return "test";

        //skapar upp ett item där utgångsdatumet redan nåtts
        Item a4 = new Item("bb", "Vill någon köpa mä?", 25, new Date(), 1, "https://picsum.photos/id/1/200");
        a4.setCategory(c2);
        //ändrar datumet till ett datum som gått ut
        a4.setEndTime(Calendar.getInstance().getTime());
        Calendar cNow = Calendar.getInstance();
        u2.addItem(a4);
        itemRepository.save(a4);
        //timer
        endAuctionTimer.startTimer(a4);

        //skapar upp önskat antal auktioner knutna till en användare
        //med utgångsdatum 2 dagar från nu
        List<Category> allCategories = categoryRepository.findAll();
        String imageUrl = "";
        String itemName;
        final int NROFCREATEDITEMS = 20;
        int randomPrice;
        int addedItems = 0;
        int categoryId = 0;
        //int nrOfSplits = NROFCREATEDITEMS / allCategories.size();
        int nrOfItemsInEachSplit = NROFCREATEDITEMS / allCategories.size();
        for (int i = 1; i <= NROFCREATEDITEMS; i++) {
           randomPrice =  new Random().nextInt(1500);
            //varje item får en egen bild från picsum
           imageUrl = "https://picsum.photos/id/" + i + "/200";
           itemName = "Cool item " + i;
           Item tempItem = new Item(itemName,"More information about cool " + allCategories.get(categoryId).getTitle() + " product " + i,randomPrice ,new Date(),1,imageUrl);
            //addera 2 dagar till dagens datum
            cNow.add(Calendar.DATE, 2);
            tempItem.setEndTime(cNow.getTime());
            tempItem.setCategory(allCategories.get(categoryId));
            u2.addItem(tempItem);
            itemRepository.save(tempItem);
            //starta timern för objektets utgångsdatum
            endAuctionTimer.startTimer(tempItem);
            addedItems++;

            //varje kategori får lika många items
            //ändra kategori efter önskat antal items i varje kategori
            if (addedItems == nrOfItemsInEachSplit && categoryId < allCategories.size() - 1) {
                categoryId++;
                addedItems = 0;
            }
        }
    }

    @GetMapping("/")
    public String redirectIndex() {

        return "redirect:/allitems?page=0";
    }

    @GetMapping("/allitems")
    public String index(Model model, @RequestParam(name = "page") Integer page) {
        if (page<=0 || page==null) {
            page = 0;
        }

        final int PAGESIZE = 6;
        PageRequest pagin = PageRequest.of(page, PAGESIZE);
        Page<Item> pagedResult = itemRepository.findAllByOrderByEndTimeAsc(pagin);
        List<Item> items = pagedResult.getContent();

        //HashMap som håller alla alla sidor från 0 - max antal sidor
        //räknar en sekvens
        //sätter två värden, första för display andra för att länka till rätt sida
        Map<Integer, Integer> totalPagesPairDisplay = new HashMap<>();
        for (int i = 0; i < pagedResult.getTotalPages(); i++) {
            totalPagesPairDisplay.put(i, i+1);
        }

        //getContent ger oss önskat antal inlägg per gång
        model.addAttribute("currentPageNr", pagedResult.getNumber());
        model.addAttribute("displayCurrentPageNr", pagedResult.getNumber() + 1);
        model.addAttribute("nextPageNumber", page+1);
        model.addAttribute("previousPageNumber", page-1);
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        model.addAttribute("totalItems", pagedResult.getTotalElements());
        model.addAttribute("hasNext", pagedResult.hasNext());
        model.addAttribute("hasPrevious", pagedResult.hasPrevious());
        model.addAttribute("showingNrOfPosts", PAGESIZE);


        model.addAttribute("currentCategory", new Category("All items", ""));
        model.addAttribute("totalPagesPairDisplay", totalPagesPairDisplay.entrySet());
        model.addAttribute("items",items);
        model.addAttribute("category",categoryRepository.findAll());

        return "home";
    }

    @GetMapping("/home/{id}")
    public String findByCategory(Model model,@PathVariable Integer id) {
        model.addAttribute("currentCategory", categoryRepository.findById(id).get());
        model.addAttribute("items",itemRepository.findAllItemsByCategory(id));
        model.addAttribute("category",categoryRepository.findAll());

        return "home";
    }

    @GetMapping(value = "/Itemsite")
    public String GetItemSite() {
        return "Item";
    }

    //Singelsida för ett auktionsobjekt
    @GetMapping("/auctionitem")
    public String getSingleItemPage(Model model, @RequestParam(name = "id") Integer id) {
        Item item = itemRepository.findById(id).get();

        model.addAttribute("item", itemRepository.findById(id).get());
        model.addAttribute("top3bids", bidRepository.findTop3ByItemOrderByPriceDesc(item));
        return "singleitem";
    }
}