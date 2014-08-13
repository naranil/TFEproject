from Tkinter import *

class Interface(Frame):
  
	def __init__(self, fenetre, **kwargs):
		Frame.__init__(self, fenetre, width=600, height=300, **kwargs)
        	self.pack(fill=BOTH)	
		self.message = Label(self, text="Operation termine!")
		self.message.pack()
        
		self.bouton_quitter = Button(self, text="I get it", command=self.quit)
		self.bouton_quitter.pack()
        


if __name__ == "__main__":
	fenetre = Tk()
	interface = Interface(fenetre)

	interface.mainloop()
	interface.destroy()
