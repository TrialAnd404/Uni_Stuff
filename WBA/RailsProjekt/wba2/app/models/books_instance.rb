class BooksInstance < ApplicationRecord
  
	belongs_to :book
  	belongs_to :user
  	belongs_to :user

  	validates :purchased_at, presence: true
  
	before_create :initialize
	before_save :set_due, on: :lending

  	protected
		def self.custom_select(filter)
			book_instances =  BookInstance.order('shelfmark asc')
			case filter
			when 'available'
				book_instances = book_instances.select(|a| a.lended_by_id.empty?)
			when 'lent'
				book_instances = book_instances.select(|a| a.lended_by_id.empty?)
			when 'overdue'
				book_instances = book_instances.select(|a| a.lended_by_id.empty?)
			when 'reserved'
				book_instances = book_instances.select(|a| a.lended_by_id.empty?)
			end
			return book_instances
		end

		def set_due
			self.due_at = self.checkout_at + (14*24*60*60)
		end

		def initialize
			self.purchased_at = Date.today
		end

		def toString
		
		end
end
