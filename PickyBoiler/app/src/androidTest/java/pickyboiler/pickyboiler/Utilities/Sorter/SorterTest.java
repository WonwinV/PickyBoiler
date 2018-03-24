package pickyboiler.pickyboiler.Utilities.Sorter;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wonwi on 3/23/2018.
 */
public class SorterTest {
    @Test
    public void computeScoreTestGood() throws Exception {
        Sorter sort = new Sorter();

        JSONObject good = new JSONObject("{\"Location\":\"Hillenbrand\",\"Status\":\"Open\",\"AllMeal\":{\"Garden Turkey Wrap\":{\"ID\":\"96b45217-4e22-46cb-ba95-68b56a8f3ac4\",\"allergens\":[]},\"Create To Order Quesadillas\":{\"ID\":\"24d7a7f9-fd9e-45ff-a604-a8c36a300094\",\"allergens\":[]},\"MYO Pho Noodle Bowl\":{\"ID\":\"82b81d99-dfe1-409c-9f51-89587498ea14\",\"allergens\":[]},\"Waffle Fries\":{\"ID\":\"b3d71414-03e8-4b4d-91b1-04cfc8698808\",\"allergens\":[\"Soy\"]},\"Hamburger\":{\"ID\":\"b82a53af-81b4-4ea0-a5b2-c0eec2a6c1bb\",\"allergens\":[]},\"Loaded Potato Soup\":{\"ID\":\"e8f7bba6-99a2-464a-adce-d1347c63d794\",\"allergens\":[\"Gluten\",\"Milk\",\"Wheat\"]},\"Cajun Chicken Breast\":{\"ID\":\"ccf7765e-1870-44ae-b562-5b444302309f\",\"allergens\":[]},\"Tomato Basil Open Face Cheese Sandwich\":{\"ID\":\"57cb1bb4-56db-498f-bbb7-9094d01edf04\",\"allergens\":[\"Gluten\",\"Milk\",\"Soy\",\"Wheat\"]},\"Beef Stroganoff\":{\"ID\":\"903fa094-7b83-4ec1-9d30-167ef839d7b6\",\"allergens\":[\"Gluten\",\"Milk\",\"Soy\",\"Wheat\"]},\"Egg Noodles\":{\"ID\":\"a020587e-2870-4eda-b9cd-b8ce3688cda0\",\"allergens\":[\"Eggs\",\"Gluten\",\"Vegetarian\",\"Wheat\"]},\"California Vegetables\":{\"ID\":\"bc581765-edea-4776-8ad2-aa09cd0882d1\",\"allergens\":[\"Vegetarian\"]},\"Vegan Grilled Zucchini\":{\"ID\":\"38ce3f45-44a6-4487-bc7f-f88d8f080e7c\",\"allergens\":[\"Vegetarian\"]},\"Wings Your Way\":{\"ID\":\"5ecfa09e-9cdc-4ba6-945e-83b050ec255c\",\"allergens\":[]},\"Jamaican Jerk  Dry Rub\":{\"ID\":\"1f272faf-2ec0-4c03-8c1b-3f9b9e2fc8b1\",\"allergens\":[]},\"Chinese BBQ Wing Sauce\":{\"ID\":\"124ae039-832a-4598-b513-524e337b1d6a\",\"allergens\":[\"Gluten\",\"Milk\",\"Soy\",\"Wheat\"]},\"Dip It Bar\":{\"ID\":\"ce49a617-feda-4f1e-895b-2e61234f8b69\",\"allergens\":[]},\"Sliced Honey Baked Ham \":{\"ID\":\"2f782739-a12f-4b07-b8b6-a7e32f681da6\",\"allergens\":[]},\"Deli Subs\":{\"ID\":\"0c721f3d-972c-4a55-b32d-4ee7ac90157d\",\"allergens\":[]},\"Scrambled Eggs\":{\"ID\":\"6c883ba0-e283-4086-ab01-e181a6615435\",\"allergens\":[\"Eggs\",\"Milk\",\"Vegetarian\"]},\"Smokey Links\":{\"ID\":\"c63a2a32-09d2-4d60-a8fc-4005e1a738d5\",\"allergens\":[]},\"Hash Brown Casserole\":{\"ID\":\"a34d226d-bd52-4e9a-b17e-6dc3c44c4c1e\",\"allergens\":[]},\"Fresh Fruit Bar\":{\"ID\":\"3f2ffe26-14b2-462d-903f-3f4977159399\",\"allergens\":[]},\"Cobb Salad\":{\"ID\":\"8bd98f31-4cd0-4f0f-b1af-023540b738be\",\"allergens\":[\"Eggs\",\"Fish\",\"Gluten\",\"Milk\",\"Soy\",\"Wheat\"]},\"Applesauce\":{\"ID\":\"78c71078-ecb0-423f-a221-b344b4e3be4f\",\"allergens\":[]},\"Delicious Glazed Lemon Cake\":{\"ID\":\"e7580022-f2c8-42c0-b31a-612bcf7a82de\",\"allergens\":[\"Eggs\",\"Gluten\",\"Milk\",\"Soy\",\"Wheat\"]},\"Chocolate Chip Cookie\":{\"ID\":\"6f6ef6dc-a4ac-47d1-8295-e82c302dba20\",\"allergens\":[\"Eggs\",\"Gluten\",\"Milk\",\"Soy\",\"Wheat\"]},\"Chocolate Pudding\":{\"ID\":\"20f23ea3-9fce-4def-b671-ff32e00e096c\",\"allergens\":[\"Milk\",\"Soy\"]},\"Sugar Cookie\":{\"ID\":\"e6f64b96-5602-4649-a2ac-6ee0165afd4f\",\"allergens\":[\"Eggs\",\"Gluten\",\"Milk\",\"Soy\",\"Wheat\"]}},\"VegetarianCount\":4,\"AllergenCount\":{\"Milk\":10,\"Gluten\":9,\"Fish\":1,\"Eggs\":6,\"Vegetarian\":4,\"Soy\":9,\"Wheat\":9},\"StartTime\":\"10:00:00\",\"EndTime\":\"14:00:00\",\"URL\":\"https:\\/\\/dining.purdue.edu\\/menus\\/Hillenbrand\\/\"}");
        JSONObject empty = new JSONObject("{}");
        //JSONObject malformed = new JSONObject("{\"Location\":\"Hillenbrand\",\"Status\":\"Open\",\"AllMeal\":{},{\"Garden Turkey Wrap\":{\"ID\":\"96b45217-4e22-46cb-ba95-68b56a8f3ac4\",\"allergens\":[]},\"Create To Order Quesadillas\":{\"ID\":\"24d7a7f9-fd9e-45ff-a604-a8c36a300094\",\"allergens\":[]},\"MYO Pho Noodle Bowl\":{\"ID\":\"82b81d99-dfe1-409c-9f51-89587498ea14\",\"allergens\":[]},\"Waffle Fries\":{\"ID\":\"b3d71414-03e8-4b4d-91b1-04cfc8698808\",\"allergens\":[\"Soy\"]},\"Hamburger\":{\"ID\":\"b82a53af-81b4-4ea0-a5b2-c0eec2a6c1bb\",\"allergens\":[]},\"Loaded Potato Soup\":{\"ID\":\"e8f7bba6-99a2-464a-adce-d1347c63d794\",\"allergens\":[\"Gluten\",\"Milk\",\"Wheat\"]},\"Cajun Chicken Breast\":{\"ID\":\"ccf7765e-1870-44ae-b562-5b444302309f\",\"allergens\":[]},\"Tomato Basil Open Face Cheese Sandwich\":{\"ID\":\"57cb1bb4-56db-498f-bbb7-9094d01edf04\",\"allergens\":[\"Gluten\",\"Milk\",\"Soy\",\"Wheat\"]},\"Beef Stroganoff\":{\"ID\":\"903fa094-7b83-4ec1-9d30-167ef839d7b6\",\"allergens\":[\"Gluten\",\"Milk\",\"Soy\",\"Wheat\"]},\"Egg Noodles\":{\"ID\":\"a020587e-2870-4eda-b9cd-b8ce3688cda0\",\"allergens\":[\"Eggs\",\"Gluten\",\"Vegetarian\",\"Wheat\"]},\"California Vegetables\":{\"ID\":\"bc581765-edea-4776-8ad2-aa09cd0882d1\",\"allergens\":[\"Vegetarian\"]},\"Vegan Grilled Zucchini\":{\"ID\":\"38ce3f45-44a6-4487-bc7f-f88d8f080e7c\",\"allergens\":[\"Vegetarian\"]},\"Wings Your Way\":{\"ID\":\"5ecfa09e-9cdc-4ba6-945e-83b050ec255c\",\"allergens\":[]},\"Jamaican Jerk  Dry Rub\":{\"ID\":\"1f272faf-2ec0-4c03-8c1b-3f9b9e2fc8b1\",\"allergens\":[]},\"Chinese BBQ Wing Sauce\":{\"ID\":\"124ae039-832a-4598-b513-524e337b1d6a\",\"allergens\":[\"Gluten\",\"Milk\",\"Soy\",\"Wheat\"]},\"Dip It Bar\":{\"ID\":\"ce49a617-feda-4f1e-895b-2e61234f8b69\",\"allergens\":[]},\"Sliced Honey Baked Ham \":{\"ID\":\"2f782739-a12f-4b07-b8b6-a7e32f681da6\",\"allergens\":[]},\"Deli Subs\":{\"ID\":\"0c721f3d-972c-4a55-b32d-4ee7ac90157d\",\"allergens\":[]},\"Scrambled Eggs\":{\"ID\":\"6c883ba0-e283-4086-ab01-e181a6615435\",\"allergens\":[\"Eggs\",\"Milk\",\"Vegetarian\"]},\"Smokey Links\":{\"ID\":\"c63a2a32-09d2-4d60-a8fc-4005e1a738d5\",\"allergens\":[]},\"Hash Brown Casserole\":{\"ID\":\"a34d226d-bd52-4e9a-b17e-6dc3c44c4c1e\",\"allergens\":[]},\"Fresh Fruit Bar\":{\"ID\":\"3f2ffe26-14b2-462d-903f-3f4977159399\",\"allergens\":[]},\"Cobb Salad\":{\"ID\":\"8bd98f31-4cd0-4f0f-b1af-023540b738be\",\"allergens\":[\"Eggs\",\"Fish\",\"Gluten\",\"Milk\",\"Soy\",\"Wheat\"]},\"Applesauce\":{\"ID\":\"78c71078-ecb0-423f-a221-b344b4e3be4f\",\"allergens\":[]},\"Delicious Glazed Lemon Cake\":{\"ID\":\"e7580022-f2c8-42c0-b31a-612bcf7a82de\",\"allergens\":[\"Eggs\",\"Gluten\",\"Milk\",\"Soy\",\"Wheat\"]},\"Chocolate Chip Cookie\":{\"ID\":\"6f6ef6dc-a4ac-47d1-8295-e82c302dba20\",\"allergens\":[\"Eggs\",\"Gluten\",\"Milk\",\"Soy\",\"Wheat\"]},\"Chocolate Pudding\":{\"ID\":\"20f23ea3-9fce-4def-b671-ff32e00e096c\",\"allergens\":[\"Milk\",\"Soy\"]},\"Sugar Cookie\":{\"ID\":\"e6f64b96-5602-4649-a2ac-6ee0165afd4f\",\"allergens\":[\"Eggs\",\"Gluten\",\"Milk\",\"Soy\",\"Wheat\"]}},\"VegetarianCount\":4,\"AllergenCount\":{\"Milk\":10,\"Gluten\":9,\"Fish\":1,\"Eggs\":6,\"Vegetarian\":4,\"Soy\":9,\"Wheat\":9},}}}\"StartTime\":\"10:00:00\",\"EndTime\":\"14:00:00\",\"URL\":\"https:\\/\\/dining.purdue.edu\\/menus\\/Hillenbrand\\/\"}");

        assertEquals("{\"computedRanking\":28,\"favoriteCounts\":[]}", sort.computeScore(null, good).toString());
    }

    @Test
    public void computeScoreTestEmpty() throws Exception {
        Sorter sort = new Sorter();

        JSONObject empty = new JSONObject("{}");
        assertEquals("{}", sort.computeScore(null, empty).toString());
    }

    @Test
    public void computeScoreTestNull() throws Exception {
        Sorter sort = new Sorter();

        JSONObject nullObj = null;
        assertEquals("{}", sort.computeScore(null, null).toString());
    }

}