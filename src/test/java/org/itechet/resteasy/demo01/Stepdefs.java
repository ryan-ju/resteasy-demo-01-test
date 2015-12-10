package org.itechet.resteasy.demo01;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.itechet.resteasy.demo01.models.Item;
import org.itechet.resteasy.demo01.models.ItemList;

import java.util.List;

import static com.jayway.restassured.RestAssured.*;
import static com.google.common.truth.Truth.assert_;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by ryan on 08/12/15.
 */
public class Stepdefs extends Base {

    List<Item> items = null;
    String addedItemId = null;
    List<Item> cartItems = null;

    @After("@CartModified")
    public void resetCart() {
        ItemList itemList = when().
                get("/cart").
                as(ItemList.class);

        if (itemList.getItems() != null) {
            for (Item item : itemList.getItems()) {
                given().
                        queryParam("itemId", item.getId()).
                        when().
                        delete("/cart").
                        then().
                        statusCode(204);
            }
        }
    }

    @When("^a user gets catalogue$")
    public void a_user_gets_catalogue() throws Throwable {
        ItemList itemList = when().
                get("/catalogue").
                as(ItemList.class);

        items = itemList.getItems();
    }

    @Then("^she should get some items$")
    public void she_should_get_some_items() throws Throwable {
        assert_().that(items).isNotEmpty();
    }

    @And("^the items should be:$")
    public void the_items_should_be(DataTable dt) throws Throwable {
        List<Item> expectedItems = dt.asList(Item.class);
        assert_().that(items).containsExactlyElementsIn(expectedItems);
    }

    @When("^a user adds the item with id \"([^\"]*)\"$")
    public void a_user_adds_the_item_with_id(String id) throws Throwable {
        given().
                queryParam("itemId", id).
                when().
                post("/cart").
                then().
                statusCode(equalTo(200));

        addedItemId = id;
    }

    @And("^she gets cart$")
    public void she_gets_cart() throws Throwable {
        ItemList itemList = when().
                get("/cart").
                as(ItemList.class);
        cartItems = itemList.getItems();
    }

    @Then("^she should get back one item$")
    public void she_should_get_back_one_item() throws Throwable {
        assert_().that(cartItems).hasSize(1);
    }

    @And("^the item have id = \"([^\"]*)\", name = \"([^\"]*)\" and description = \"([^\"]*)\"$")
    public void the_item_have_id_name_and_description_(String id, String name, String description) throws Throwable {
        Item item = cartItems.get(0);
        assert_().that(item.getId()).isEqualTo(id);
        assert_().that(item.getName()).isEqualTo(name);
        assert_().that(item.getDescription()).isEqualTo(description);
    }

    @And("^she deletes the item$")
    public void she_deletes_the_item() throws Throwable {
        given().
                queryParam("itemId", addedItemId).
                when().
                delete("/cart").
                then().
                statusCode(204);
    }

    @Then("^she should get back empty cart$")
    public void she_should_get_back_empty_cart() throws Throwable {
        assert_().that(cartItems).isEmpty();
    }
}
