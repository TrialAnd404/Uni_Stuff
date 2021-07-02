require "test_helper"

class BooksInstancesControllerTest < ActionDispatch::IntegrationTest
  setup do
    @books_instance = books_instances(:one)
  end

  test "should get index" do
    get books_instances_url
    assert_response :success
  end

  test "should get new" do
    get new_books_instance_url
    assert_response :success
  end

  test "should create books_instance" do
    assert_difference('BooksInstance.count') do
      post books_instances_url, params: { books_instance: { book_id: @books_instance.book_id, checkout_at: @books_instance.checkout_at, due_at: @books_instance.due_at, purchased_at: @books_instance.purchased_at, returned_at: @books_instance.returned_at, shelfmark: @books_instance.shelfmark, user_id: @books_instance.user_id } }
    end

    assert_redirected_to books_instance_url(BooksInstance.last)
  end

  test "should show books_instance" do
    get books_instance_url(@books_instance)
    assert_response :success
  end

  test "should get edit" do
    get edit_books_instance_url(@books_instance)
    assert_response :success
  end

  test "should update books_instance" do
    patch books_instance_url(@books_instance), params: { books_instance: { book_id: @books_instance.book_id, checkout_at: @books_instance.checkout_at, due_at: @books_instance.due_at, purchased_at: @books_instance.purchased_at, returned_at: @books_instance.returned_at, shelfmark: @books_instance.shelfmark, user_id: @books_instance.user_id } }
    assert_redirected_to books_instance_url(@books_instance)
  end

  test "should destroy books_instance" do
    assert_difference('BooksInstance.count', -1) do
      delete books_instance_url(@books_instance)
    end

    assert_redirected_to books_instances_url
  end
end
