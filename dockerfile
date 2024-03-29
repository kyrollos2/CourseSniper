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
#Runs Script (-u argument makes it so you can see the logs in real time)
CMD ["python", "-u", "pull4db.py"]
#--------------------------------------------------------------------------
#MOST IMPORTANT BEFORE ANYTHING MAKE SURE YOU HAVE DOCKER DESKTOP INSTALLED AND RUNNING
#https://www.docker.com/products/docker-desktop/
#--------------------------------------------------------------------------
#"WHAT TO INPUT INTO THE COMMAND LINE"

# 1. Use the 'cd' command to change the current directory to the location of this dockerfile
#   EX: $cd desktop/course-sniper
# 2. Type in 'docker build -t course-sniper .' This will create the docker image.
#   If it has been created successfully, you should see it in docker.
# 3. Type in 'docker run course-sniper' This will run the image in a container.
#   If it is working correctly, you should see the same output as running the script on VSCode.
#--------------------------------------------------------------------------