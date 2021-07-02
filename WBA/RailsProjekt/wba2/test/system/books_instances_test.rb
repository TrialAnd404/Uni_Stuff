require "application_system_test_case"

class BooksInstancesTest < ApplicationSystemTestCase
  setup do
    @books_instance = books_instances(:one)
  end

  test "visiting the index" do
    visit books_instances_url
    assert_selector "h1", text: "Books Instances"
  end

  test "creating a Books instance" do
    visit books_instances_url
    click_on "New Books Instance"

    fill_in "Book", with: @books_instance.book_id
    fill_in "Checkout at", with: @books_instance.checkout_at
    fill_in "Due at", with: @books_instance.due_at
    fill_in "Purchased at", with: @books_instance.purchased_at
    fill_in "Returned at", with: @books_instance.returned_at
    fill_in "Shelfmark", with: @books_instance.shelfmark
    fill_in "User", with: @books_instance.user_id
    click_on "Create Books instance"

    assert_text "Books instance was successfully created"
    click_on "Back"
  end

  test "updating a Books instance" do
    visit books_instances_url
    click_on "Edit", match: :first

    fill_in "Book", with: @books_instance.book_id
    fill_in "Checkout at", with: @books_instance.checkout_at
    fill_in "Due at", with: @books_instance.due_at
    fill_in "Purchased at", with: @books_instance.purchased_at
    fill_in "Returned at", with: @books_instance.returned_at
    fill_in "Shelfmark", with: @books_instance.shelfmark
    fill_in "User", with: @books_instance.user_id
    click_on "Update Books instance"

    assert_text "Books instance was successfully updated"
    click_on "Back"
  end

  test "destroying a Books instance" do
    visit books_instances_url
    page.accept_confirm do
      click_on "Destroy", match: :first
    end

    assert_text "Books instance was successfully destroyed"
  end
end
