FROM python:3.10

WORKDIR /app

#Copy from ./SRC to /app
COPY ./scripts/pull4db.py /app

COPY ./requirements.txt /app

#Installs necessary modules
RUN pip install --trusted-host pypi.python.org -r requirements.txt

#RUN docker run -d -p 4444:4444 -p 7900:7900 --shm-size="2g" selenium/standalone-chrome:latest

#Installs Google
RUN apt-get install -y wget
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \ 
    && echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list
RUN apt-get update && apt-get -y install google-chrome-stable

#Runs Script (-u argument makes it so you can see the logs in real time)
CMD ["python", "-u", "pull4db.py"]

#--------------------------------------------------------------------------
#MOST IMPORTANT BEFORE ANYTHING MAKE SURE YOU HAVE DOCKER DESKTOP INSTALLED
#https://www.docker.com/products/docker-desktop/

#"WHAT TO INPUT INTO THE GIT COMMAND LINE"

#Use the 'cd' command to change the current directory to the location of this dockerfile
#Type in 'docker build -t course-sniper .' This will create the docker image.
#Type in 'docker run course-sniper' This will run the image in a container
#--------------------------------------------------------------------------
