###################################################################################
### 2. PyTorch
###################################################################################
import torch
print(torch.__version__)


###################################################################################
### 2. Daten einlesen
###################################################################################
'''
Prof. Panitz macht von sich tägliche Selfies. Diese lesen wir ein und verwenden Sie 
für PanitzNetz. In der Datei

    panitznet.zip 

die Sie aus dem Read.MI heruntergeladen haben befinden sich die Selfies unter 

    imgs/small/*

Führen Sie den folgenden Code aus. Passen Sie vorher ggfs. die Variable PATH an. 
Es sollten ca. 1800 Bilder der Dimension 32×32×3 eingelesen werden. Am Ende 
wird eines der Bilder als Beispiel geplottet.

HINWEIS: Sollten Sie auf dem Server supergpu arbeiten wollen, werden beim Plotten
         von Daten evtl. noch Fehler auftauchen. Wir besprechen dies im Praktikum.
'''

from datetime import timedelta, date
from PIL import Image
import os
import numpy as np
import matplotlib.pyplot as plt

PATH = 'imgs/small' # FIXME
D = 32


def read_jpg(path):
    '''liest ein JPEG ein und gibt ein DxDx3-Numpy-Array zurück.'''
    img = Image.open(path)
    w,h = img.size
    # schneide etwas Rand ab.
    img = img.crop((5, 24, w-5, h-24))
    # skaliere das Bild
    img = img.resize((D,D), Image.ANTIALIAS)
    img = np.asarray(img)
    return img


def read_panitz(directory):
    
    def daterange(start_date, end_date):
        for n in range(int ((end_date - start_date).days)):
            yield start_date + timedelta(n)

    start_date = date(2010, 10, 30)
    end_date   = date(2019,  1,  1)

    imgs = []
    
    for date_ in daterange(start_date, end_date):
        img_path = '%s/small-b%s.jpg' %(directory, date_.strftime("%Y%m%d")) 
        if os.path.exists(img_path):
            img = read_jpg(img_path)
            imgs.append(img)
            
    return np.array(imgs)
    

imgs = read_panitz(PATH)

print('Dimension der gelesenen Bilder:', imgs.shape)

# zeigt ein Bild
plt.imshow(imgs[17])
plt.show()




###################################################################################
### 3. Hifsmethode zum Plotten
###################################################################################
'''
Während wir PanitzNet trainieren, möchten wir beobachten wie die Rekonstruktionen
des Netzes den Eingabebildern immer ähnlicher werden. Hierzu können Sie die 
folgende Methode verwenden: Übergeben Sie eine Liste von z.B. 10 Bildern (imgs) 
und die  zugehörigen Rekonstruktionen Ihres Netzes (recs) als Listen von 
numpy-Arrays. Es sollte ein Plot erstellt werden, in dem Sie neben jedem Bild 
die Rekonstruktion sehen, ähnlich dem Bild

   panitzplot.png

Überprüfen Sie kurz die Methode, indem Sie 10 zufällige Bilder und (anstelle der 
noch nicht vorhandenen Rekonstruktionen) noch einmal dieselben Bilder übergeben. 
'''
import math

def plot_reconstructions(imgs, recs, iteration=None):

    # Erstellt ein NxN-Grid zum Plotten der Bilder
    N = int(np.ceil(math.sqrt(2*len(imgs))))
    f, axarr = plt.subplots(nrows=N, ncols=N, figsize=(18,18))

    # Fügt die Bilder in den Plot ein
    for i in range(min(len(imgs),100)):
        axarr[2*i//N,2*i%N].imshow(imgs[i].reshape((D,D,3)), 
                                   interpolation='nearest')
        axarr[(2*i+1)//N,(2*i+1)%N].imshow(recs[i].reshape((D,D,3)), 
                                           interpolation='nearest')
    f.tight_layout()
    plt.show()
    #plt.savefig('recs-%.4d.png' %iteration)
    plt.close()

    
###################################################################################
### 4. Vorverarbeitung
###################################################################################
'''
Momentan ist jedes der Bild noch ein D×D×3-Tensor. Machen Sie hieraus einen 
eindimensionalen Vektor. Skalieren Sie den Pixelbereich außerdem von 0,...,255 
auf [0,1].
'''

import torchvision

imgs2 = imgs
t = torch.tensor(imgs)
picSize = 32*32*3
size = len(imgs)*picSize
# t = t.reshape((len(imgs), picSize))
t = torchvision.transforms.functional.to_tensor(
    imgs.reshape((len(imgs), picSize))
)


###################################################################################
### 5. Sie sind am Zug!
###################################################################################
'''
Implementieren Sie PanitzNet, d.h. erstellen Sie die Netzstruktur und trainieren
Sie Ihr Netz. Orientieren Sie sich am in der Vorlesung vorgestellten Programmgerüst.
'''
import torch.nn as nn

print("Torch:", t)
num_epochs = 100
learning_rate = 1e-3

class PanitzNet(nn.Module):
    print(nn.Module)

    def __init__(self):
        super(PanitzNet, self).__init__()
        self.encoder = nn.Sequential(
            nn.Linear(picSize, 1000),
            nn.ReLU(True),
            nn.Linear(1000, 100),
            nn.ReLU(True),
            nn.Linear(100, 50)
        )
        self.decoder = nn.Sequential(
            nn.Linear(50, 100),
            nn.ReLU(True),
            nn.Linear(100, 1000),
            nn.ReLU(True),
            nn.Linear(1000, picSize),
            nn.Tanh()
        )

    def forward(self, x):
        x = self.encoder(x)
        x = self.decoder(x)
        return x

from torchvision import utils
device = torch.device("cuda:0" if torch.cuda.is_available() else "cpu")

def to_img(x):
    x = 0.5 * (x + 1)
    x = x.clamp(0, 1)
    x = x.view(x.size(0), 3, 32, 32)
    print(x)
    return x

if not os.path.exists('./mlp_img'):
    os.mkdir('./mlp_img')

def run_training(net, pics):

    criterion = nn.MSELoss()
    optimizer = torch.optim.Adam(net.parameters(), lr=learning_rate, weight_decay=1e-5)

    print(imgs)
    for epoch in range(num_epochs):
        for pic in pics:
            # img, _ = data
            # img = img.view(img.size(0), -1)
            pic = torch.autograd.Variable(pic)
            # img = img.to(device)

            # forward
            output = net(pic)
            loss = criterion(output, pic)

            # backward
            optimizer.zero_grad()
            loss.backward()
            optimizer.step()

        # log
        print('epoch [{}/{}], loss:{:.4f}'
              .format(epoch + 1, num_epochs, loss.data))

        if epoch % 20 == 0:
            # pic = to_img(output.cpu().data)
            # utils.save_image(pic, './mlp_img/image_{}.png'.format(epoch))

            print(len(t[0][epoch]))
            print(output.cpu().data[epoch])
            # output.cpu().data.reshape((len(imgs), picSize))
            print(output.cpu().data)
            plot_reconstructions(list(t[0]), list(output.cpu().data[0]))

# define the network+data, and train.
net = PanitzNet()
run_training(net, t)
