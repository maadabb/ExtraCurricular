# DO NOT EDIT THE FOLLOWING LINES
# COURSE CPSC 231 FALL 2021
# INSTRUCTOR: Jonathan Hudson
# Wi52C3g7ZzkJ7XBcVRHY
# DO NOT EDIT THE ABOVE LINES

# Tutorial: Marcus
# ID: 30127307
# Date: November 23rd 2021
#Name: Maad Abbasi
#Tutorial: T05
#Description: Asks a user for star files and constellation files. If user enters both a valid star and constellation file it draws the stars and constellation on turtle.

import sys
import os
import turtle

TICK = 3
# STARTER CONSTANTS
LABEL = 2
BACKGROUND_COLOR = "black"
WIDTH = 600
HEIGHT = 600
# AXIS CONSTANTS
AXIS_COLOR = "blue"
# STAR CONSTANTS
STAR_COLOR = "white"
STAR_COLOR2 = "grey"



def screencoord(x,y):
    """
    Convert a calculator (x,y) to a pixel (screen_x, screen_y) based on origin location and ratio
    :param x: Calculator x
    :param y: Calculator y
    :return: (screenx, screeny) pixel version of calculator (x,y)
    """
    x = float(x)
    y = float(y)
    screenx = (WIDTH/2) + (WIDTH/2) * x
    screeny = (HEIGHT/2)+ (HEIGHT/2) * y
    return screenx, screeny


def drawxticks(pointer):
    """
    Drawing the ticks of an x axis using screenx and screeny values. With the highest tick value being one and lowest being -1
    :param pointer: Turtle pointer to draw with
    :return: None
    """
    x = 0
    y = 0


    while x<1:
        screenx, screeny = screencoord(x, y)
        pointer.goto(screenx,screeny)
        pointer.down()
        pointer.goto(screenx,screeny + TICK)
        pointer.goto(screenx, screeny - TICK)
        pointer.up()
        pointer.goto(screenx, screeny + LABEL)
        pointer.write(str(x))
        x = x + 0.25

    while x>=-1:
        screenx, screeny = screencoord(x, y)
        pointer.goto(screenx,screeny)
        pointer.down()
        pointer.goto(screenx,screeny + TICK)
        pointer.goto(screenx, screeny - TICK)
        pointer.up()
        pointer.goto(screenx,screeny+LABEL)
        pointer.write(str(x))
        x = x - 0.25

def drawyticks(pointer):
    """
    Drawing ticks for the y axis using screenx and screeny values. With the highest tick value being 1 and the lowest -1
    :param pointer: Turtle pointer to draw with
    :return: None
    """
    x = 0
    y = 0

    while y<1:
        screenx, screeny = screencoord(x, y)
        pointer.goto(screenx,screeny)
        pointer.down()
        pointer.goto(screenx+TICK,screeny)
        pointer.goto(screenx-TICK,screeny)
        pointer.up()
        pointer.goto(screenx + LABEL, screeny)
        pointer.write(str(y))
        y = y + 0.25
    while y >= -1:
        screenx, screeny = screencoord(x, y)
        pointer.goto(screenx,screeny)
        pointer.down()
        pointer.goto(screenx+TICK,screeny)
        pointer.goto(screenx-TICK,screeny)
        pointer.up()
        pointer.goto(screenx + LABEL, screeny)
        pointer.write(str(y))
        y = y - 0.25

def drawaxis(pointer):
    """
    Drawing the x and y axises for a graph and then writing the ticks on the two axises
    :param pointer: Turtle pointer to draw with
    :return: None
    """
    pointer.color(AXIS_COLOR)
    pointer.goto(0,HEIGHT/2)
    pointer.down()
    pointer.goto(WIDTH,HEIGHT/2)
    pointer.up()
    pointer.goto(WIDTH/2,0)
    pointer.down()
    pointer.goto(WIDTH/2,HEIGHT)
    drawyticks(pointer)
    drawxticks(pointer)
    pointer.up()






def setup():
    """
    Setup the turtle window and return drawing pointer
    :return: Turtle pointer for drawing
    """
    turtle.bgcolor(BACKGROUND_COLOR)
    turtle.setup(WIDTH, HEIGHT, 0, 0)
    screen = turtle.getscreen()
    screen.delay(delay=0)
    screen.setworldcoordinates(0, 0, WIDTH, HEIGHT)
    pointer = turtle
    pointer.hideturtle()
    pointer.speed(0)
    pointer.up()
    return pointer






def starinfo(line):
    """
      The purpose of this function is to seperate a list which has all the information of the star and only get the x,y,mag,and list of names and then return those values which have been taken from the list
      :param line: list of stars with all the corresponding values (before it has been stripped)
      :return x: returns the float value of the x calculator coordinate of the star
      :return y: returns the float value of the y calculator coordinate of the star
      :return mag: returns the float value of the magnitude of the star
      :return namelist: returns a list which includes the names of the stars
      """
    try:
        line = line.split(",")
        x = float(line[0])
        y = float(line[1])
        mag = float(line[4])
        names = line[6].strip("\n")
        namelist = names.split(";")
        return x,y,mag,namelist

    except:
        print("You have entered an invalid file.")
        sys.exit()

def starreader (file):
    """
        The purpose of this function is to open a stars file entered by the user, the function then creates a list called allstars which has a tuple of the x,y,mag and namelist values. The function then creates a dictionary in which the names of the stars are the keys and the values are the tuple of x,y,mag,namelist
        :param file: file is the user entered stars file that they enter in
        :return allstars: returns a list which has the x,y,mag,namelist information of a star
        :return starnamesdict: returns a dictionary where namelist is the key and the tuple of x,y,mag,namelist is the value
         """
    f = open(file)
    starlist = f.readlines()
    length = len(starlist)
    allstars = []
    starnamesdict = {}
    counter = 0
    while counter < length:
        data = starlist[counter]
        x,y,mag,namelist = starinfo(data)
        strx = str(x)
        stry = str(y)
        strmag = str(mag)
        lst = ((strx,stry,strmag,namelist))
        counter = counter + 1
        allstars += [lst]
        for i in namelist:
            if i != "":
                starnamesdict[i] = lst
                print(i, "is at" "("+str(x)+","+str(y)+")","with magnitude",mag)
    f.close()
    return allstars,starnamesdict



def drawstars(pointer,x,y,mag,names,wantnames,colour,):
    """
        The purpose of this function is to use the turtle function in order to draw a star, and then check if one of the arguments entered was -names and if it was then also write the name with the star.
        :param pointer: Turtle pointer to draw with
        :param x: the float value of the x calculator coordinate of the star
        :param y: the float value of the y calculator coordinate of the star
        :param names: the name of a star
        :param wantnames: a boolean to check wether user entered -names
        :param colour: the colour of the star, this colour is white if the star has a name and grey if the value does not
        :return None:
    """
    pointer.color(colour)
    x,y = screencoord(x,y)
    magfloat = float(mag)
    diameter = 10/(magfloat+2)
    pointer.goto(x,y)
    pointer.down()
    pointer.dot(diameter)
    pointer.up()
    if wantnames == True:
        pointer.write(names[0], font=("Arial", 5, "normal"))


def constellationreader(constellationfile):
    """
        The purpose of this function is to use the turtle function in order to draw a star, and then check if one of the arguments entered was -names and if it was then also write the name with the star.
        :param constellationfile: The constellation file entered by the user
        :return listofstaredges: A list with names of stars which contain the value of the edges for where the constellation will be drawn from
        :return constellationname: The name of the constellation. Ex(BigDipper)
    """
    f = open(constellationfile)
    constellationlist = f.readlines()
    constellationname = constellationlist[0]
    constellationname = constellationname.rstrip()
    staredges = constellationlist[1:]
    listofstaredges = []
    constellationstars = []
    for i in staredges:
        i = i.rstrip()
        stars = i.split(",")
        listofstaredges += [stars]
        for star in stars:
            if star not in constellationstars:
                constellationstars.append(star)
    f.close()
    print(constellationname,"Contains the following stars:",constellationstars)
    return listofstaredges,constellationname

def get_color(constellationcounter):
    """
    Get color for an equation based on counter of how many equations have been drawn (this is the xth equation)
    :param equation_counter: Number x, for xth equation being drawn
    :return: A string color for turtle to use
    """
    # DEFAULT return black, needs to be changed
    if (constellationcounter%3) == 0:
        return "red"
    elif (constellationcounter%3) == 1:
        return "green"
    elif (constellationcounter%3) == 2:
        return "yellow"

def constellationdraw(pointer,starnamesdict,colour,listofstaredges):
    """
        The purpose of this function is to draw the lines in order to create the constellation, this is done by drawing from the first edge of the constellation to another
        :param pointer:
        :param starnamesdict: A dictionary where namelist of the star is the key and the tuple of star values (x,y,mag,namelist) is the value
        :param colour: The colour of the constellation. (based on equation counter)
        :param listofstaredges: A list with names of stars which contain the value of the edges for where the constellation will be drawn from
        :return None
    """
    pointer.color(colour)
    for i in listofstaredges:
        x = starnamesdict[i[0]][0]
        y = starnamesdict[i[0]][1]
        screenx,screeny = screencoord(x,y)
        pointer.goto(screenx,screeny)
        pointer.down()
        x2 = starnamesdict[i[1]][0]
        y2 = starnamesdict[i[1]][1]
        screenx2,screeny2 = screencoord(x2,y2)
        pointer.goto(screenx2,screeny2)
        pointer.up()

    pass









def main():
    wantnames = False
    if len(sys.argv) > 3:
        print("You have inputted too many arguments, the maximum amount of arguments is 2.")
        sys.argv(exit())

    elif len(sys.argv) == 3:
        if sys.argv[1] != "-names" and sys.argv[2] != "-names":
            print("Neither of the arguments inputted were -names, one argument must be -names.")
            sys.argv(exit())

        elif sys.argv[1] == "-names" and sys.argv[2] == "-names":
            print("Both arguments cannot be -names.")
            sys.argv(exit())

        elif sys.argv[1] == "-names" and sys.argv[2] != "-names":
            wantnames = True
            starsfilename = sys.argv[2]
            if os.path.isfile(starsfilename) == True:
                starreader(starsfilename)
            else:
                print("Invalid stars name")
                sys.exit()

        elif sys.argv[1] != "-names" and sys.argv[2] == "-names":
            wantnames = True
            starsfilename = sys.argv[1]
            if os.path.isfile(starsfilename) == True:
                starreader(starsfilename)
            else:
                print("Invalid star name")
                sys.exit()
    elif len(sys.argv) == 2:
        if sys.argv[1] == "-names":
            wantnames = True
            starsfilename = input("Enter a stars filename")
            if os.path.isfile(starsfilename) == True:
                starreader(starsfilename)

            else:
                print("Invalid star location")
                sys.exit()

        if sys.argv[1] != "-names":
            starsfilename = sys.argv[1]
            if os.path.isfile(starsfilename) == True:
                starreader(starsfilename)

            else:
                print("Invalid star file")
                sys.exit()
    elif len(sys.argv) == 1:
        starsfilename = input("Enter a stars filename")
        if os.path.isfile(starsfilename) == True:
            starreader(starsfilename)

        else:
            print("Invalid star location")
            sys.exit()

    """
    Main constellation program
    :return: None
    """
    # Handle arguments
    pointer = setup()


    drawaxis(pointer)


    allstars,starnamesdict = starreader(starsfilename)

    #This loop is used to draw the stars.
    for i in allstars:
        x = i[0]
        y = i[1]
        mag = i[2]
        names = i[3]
        if names !="":
            colour = "white"
            drawstars(pointer,x,y,mag,names,wantnames,colour)
        elif names == "":
            colour = "grey"
            drawstars(pointer,x,y,mag,names,wantnames,colour)


    constellationfile = input("Enter constellation filename")
    constellationcounter = 0
    #This loop is used to draw the constellations. Their is a constellationcounter which will be updated after a constellation has been drawn. This is used to change the color of the constellation
    while constellationfile != "":
        colour = get_color(constellationcounter)
        listofstaredges,constellationname = constellationreader(constellationfile)
        constellationdraw(pointer,starnamesdict,colour,listofstaredges)
        constellationcounter += 1
        constellationfile = input("Enter constellation filename,press enter to draw constellation")


    # Read star information from file (function)
    # Turns off draw update until turtle.update() is called

    turtle.tracer(0)
    # Draw Axes (function)
    turtle.update()
    # Draw Stars (function)
    turtle.update()
    # Loop getting filenames
    while False:
        # Read constellation file (function)
        # Draw Constellation (function)
        turtle.update()
        # Draw bounding box (Bonus) (function)
        turtle.update()
        pass




main()


print("\nClick on window to exit!\n")

turtle.exitonclick()

