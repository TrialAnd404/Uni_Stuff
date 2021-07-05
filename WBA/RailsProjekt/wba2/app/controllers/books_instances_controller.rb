class BooksInstancesController < ApplicationController
  before_action :set_books_instance, only: %i[ show edit update destroy lending reserving ]

  # GET /books_instances or /books_instances.json
  def index
    @books_instances = BooksInstance.all
  end

  # GET /books_instances/1 or /books_instances/1.json
  def show
  end

  # GET /books_instances/new
  def new
    @books_instance = BooksInstance.new
  end

  # GET /books_instances/1/edit
  def edit
  end

  # POST /books_instances or /books_instances.json
  def create
    @books_instance = BooksInstance.new(books_instance_params)

    respond_to do |format|
      if @books_instance.save
        format.html { redirect_to @books_instance, notice: "Books instance was successfully created." }
        format.json { render :show, status: :created, location: @books_instance }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @books_instance.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /books_instances/1 or /books_instances/1.json
  def update
    respond_to do |format|
      if @books_instance.update(books_instance_params)
        format.html { redirect_to @books_instance, notice: "Books instance was successfully updated." }
        format.json { render :show, status: :ok, location: @books_instance }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @books_instance.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /books_instances/1 or /books_instances/1.json
  def destroy
    @books_instance.destroy
    respond_to do |format|
      format.html { redirect_to books_instances_url, notice: "Books instance was successfully destroyed." }
      format.json { head :no_content }
    end
  end

  @books_instances = BookInstance.custom_select(params[:filter])

  def lending
	if all_params['User']{|a| }
	end
	all_params['User']{|a| @book_instance.lended_by_id = a.to_i}
  end

  def reserving
	all_params['User']{|a| @book_instance.reserved_by_id = a.to_i}
  end
  
  private
    # Use callbacks to share common setup or constraints between actions.
    def set_books_instance
      @books_instance = BooksInstance.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def books_instance_params
      params.require(:books_instance).permit(:book_id, :shelfmark, :purchased_at, :user_id, :user_id, :checkout_at, :due_at, :returned_at)
    end
end
