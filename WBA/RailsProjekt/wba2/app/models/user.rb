class User < ApplicationRecord
	has_many :book_instances

	validates :family_name, :given_name, :address, :email, :password, presence: true
	validates :email, uniqueness: true, format: {with: URI::MailTo::EMAIL_REGEXP}
	validates :password, confirmation: true, 
		format: { with: /^\w[8,]$/,
		message: 'mindestens 8 Zeichen'}

	before_destroy :has_books

	protected
		def self.custom_select(filter)
			users = User.order('family_name asc')
			case filter
			when 'has_remarks'
				users = users.select {|a| a.remarks.exists?}
			when 'has_books'
				users = users.select {|a| BookInstance.where("lended_by_id IS #{a.id}")}
			when 'has_overdue_books'
				users = users.select {|a| BookInstance.where("lended_by_id IS #{a.id} AND due_at < #{Date.today}")}
			when 'has_reservations'
				users = users.select {|a| BookInstance.where("reserved_by_id IS #{a.id}")}
			end
			return users
		end

	def short_name
		shortname = self.given_name[0]+". "+self.family_name
		return shortname
	end

	def has_books
		if BookInstance.where("lended_by_id = ?", @self.id).exists?
			throw :abort
			errors.add(:lended_by_id, "User hat noch geliehene Bücher")
		end
	end

	def toString

	end

end
