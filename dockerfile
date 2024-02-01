FROM python:3.10

WORKDIR /app

#Copy from ./SRC to /app
COPY ./scripts/pull4db.py /app

COPY ./requirements.txt /app

#Installs necessary modules
RUN pip install --trusted-host pypi.python.org -r requirements.txt

#Installs Google
RUN apt-get install -y wget
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \ 
    && echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list
RUN apt-get update && apt-get -y install google-chrome-stable

#Runs Script
CMD ["python", "pull4db.py"]

