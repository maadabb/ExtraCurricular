import requests
from bs4 import BeautifulSoup
import json

mystocks = ['VAST.L', 'ICON.L', 'PREM.L', 'BZT.L']
stockdata = []

def getData(symbol):
    headers = {'User-Agent' : 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36'}
    url = f'https://ca.finance.yahoo.com/quote/ASPL.L{symbol}'
    r = requests.get(url,headers=headers)

    soup = BeautifulSoup(r.text,'html.parser')
    
    stock = {
    'symbol': symbol,
    'price' : soup.find('span', {'class':'D(ib) Mend(20px)'}).find_all('span')[0].text,
    'change' : soup.find('span', {'class': 'D(ib) Mend(20px)'}).find_all('span')[1].text,
    }
    return stock

for item in mystocks:
    stockdata.append(getData(item))
    print('Getting:', item)

with open('stockdata.json','w') as f:
    json.dump(stockdata, f)