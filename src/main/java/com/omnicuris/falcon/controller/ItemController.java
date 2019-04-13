package com.omnicuris.falcon.controller;

import com.omnicuris.falcon.exception.DataNotFoundException;
import com.omnicuris.falcon.exception.DuplicateDataException;
import com.omnicuris.falcon.model.Item;
import com.omnicuris.falcon.model.ResponseObj;
import com.omnicuris.falcon.service.ItemService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jagadish Varma on 4/12/2019.
 */

@RestController
@RequestMapping("/item")
public class ItemController {

    private static Log log = LogFactory.getLog(ItemController.class);

    @Autowired
    ItemService itemService;

    /**
     * Adds item to db and returns added item
     * @param item
     * @return item
     */
    @PostMapping("/add")
    public ResponseEntity<?> addItem(@RequestBody Item item)  {
        ResponseObj<Item> response = new ResponseObj<>();
        try {
            Item item1 = itemService.addItem(item);
            log.info("Item " + item.getName() + " successfully added");
            response.setPayload(item1);
            response.setCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (DuplicateDataException e)    {
            log.info(e.fillInStackTrace());
            response.setCode(409);
            response.setError("Item with name " + item.getName() + " already exists");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }catch (Exception e)    {
            log.info(e.fillInStackTrace());
            response.setCode(400);
            response.setError("Unable to process your request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Updates the quantity of item by id
     * @param item
     * @return updated item
     */
    @PostMapping("/update/quantity")
    public ResponseEntity<?> updateItem(@RequestBody Item item)  {
        ResponseObj<Item> response = new ResponseObj<>();
        try {
            Item item1 = itemService.updateItem(item);
            log.info("Item " + item.getName() + " quantity successfully updated");
            response.setPayload(item1);
            response.setCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (DataNotFoundException e)    {
            log.info(e.fillInStackTrace());
            response.setError("Data not found");
            response.setCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.info(e.fillInStackTrace());
            response.setCode(400);
            response.setError("Unable to process your request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Fetches the details of Item by Id
     * @param id
     * @return Item
     */

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getItemDetails(@PathVariable("id") Long id)  {
        ResponseObj<Item> response = new ResponseObj<>();
        try {
            Optional<Item> item = itemService.getItem(id);
            if (item.isPresent()) {
                log.info("Details fetched for item id " +id);
                response.setPayload((item.get()));
                response.setCode(200);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.setError("Data not found");
            response.setCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.info(e.fillInStackTrace());
            response.setCode(400);
            response.setError("Unable to process your request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Returns the items in db with pagination
     * @param page
     * @param size
     * @return Items
     */
    @GetMapping("/details")
    public ResponseEntity<?> getAllItemDetails(@RequestParam(required = false) Integer page,
                                               @RequestParam(required = false) Integer size)  {
        ResponseObj<List<Item>> response = new ResponseObj<>();
        try {
            if(size == null || size <= 0 || size > 20)	{
                size = 10;
            }if(page == null)   {
                page = 0;
            }
            response.setPayload(itemService.getAllItems(page, size));
            response.setCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.fillInStackTrace());
            response.setError("Unable to process your request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


    }

    /**
     * Deletes an entity
     * @param id
     * @return response
     */

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> updateItem(@PathVariable("id") Long id)  {
        ResponseObj<Item> response = new ResponseObj<>();
        try {
            itemService.deleteItem(id);
            log.info("Item deleted successfully");
            response.setCode(200);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (DataNotFoundException e)    {
            log.info(e.fillInStackTrace());
            response.setError("Data not found");
            response.setCode(404);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            log.info(e.fillInStackTrace());
            response.setError("Unable to process your request");
            response.setCode(400);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }





}
