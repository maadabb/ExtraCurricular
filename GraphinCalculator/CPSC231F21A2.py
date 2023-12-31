# COURSE CPSC 231 FALL 2021
# INSTRUCTOR: Jonathan Hudson
# Tutorial: Marcus
# ID: 30127307
# Date: OCTOBER 22nd 2021
#Name: Maad Abbasi
#Tutorial: T05
# Description: The purpose of this code is to use functions and loops in order to draw an x and y axises with ticks and labels in order to create an cartesian plane on which the user can continously draw different functions with the unknown of x

from math import *
import turtle

# Constants
BACKGROUND_COLOR = "white"
WIDTH = 800
HEIGHT = 600
AXIS_COLOR = "black"
SPACING = 1
TICKSPACING = 3
LABELSPACING = 13



def get_color(equation_counter):
    """
    Get color for an equation based on counter of how many equations have been drawn (this is the xth equation)
    :param equation_counter: Number x, for xth equation being drawn
    :return: A string color for turtle to use
    """
    # DEFAULT return black, needs to be changed
    if (equation_counter%3) == 0:
        return "red"
    elif (equation_counter%3) == 1:
        return "green"
    elif (equation_counter%3) == 2:
        return "blue"



def calc_to_screen_coord(x, y, x_origin, y_origin, ratio):
    """
    Convert a calculator (x,y) to a pixel (screen_x, screen_y) based on origin location and ratio
    :param x: Calculator x
    :param y: Calculator y
    :param x_origin: Pixel x origin of pixel coordinate system
    :param y_origin: Pixel y origin of pixel coordinate system
    :param ratio: Ratio of pixel coordinate system (each 1 in calculator is worth ratio amount of pixels)
    :return: (screen_x, screen_y) pixel version of calculator (x,y)
    """

    screen_x = x_origin + (ratio*x)
    screen_y = y_origin + (ratio*y)

    return screen_x,screen_y









def calc_minmax_x(x_origin, ratio):
    """
    Calculate smallest and largest calculator INTEGER x value to draw for a 0->WIDTH of screen
    Smallest: Convert a pixel x=0 to a calculator value and return integer floor
    Largest : Convert a pixel x=WIDTH to a calculator value and return integer ceiling
    :param x_origin: Pixel x origin of pixel coordinate system
    :param ratio: Ratio of pixel coordinate system (each 1 in calculator is worth ratio amount of pixels)
    :return: (Smallest, Largest) x value to draw for a 0->WIDTH of screen
    """
    smallestx = (-x_origin)/(ratio)
    minx = int(floor(smallestx))
    largestx = (x_origin/ratio)
    maxx = int(ceil(largestx))

    return minx, maxx


def calc_minmax_y(y_origin, ratio):
    """
    Calculate smallest and largest calculator INTEGER y value to draw for a 0->HEIGHT of screen
    Smallest: Convert a pixel y=0 to a calculator value and return integer floor
    Largest : Convert a pixel y=HEIGHT to a calculator value and return integer ceiling
    :param y_origin: Pixel y origin of pixel coordinate system
    :param ratio: Ratio of pixel coordinate system (each 1 in calculator is worth ratio amount of pixels)
    :return: (Smallest, Largest) y value to draw for a 0->HEIGHT of screen
    """
    smallesty = (-y_origin/ratio)
    miny = int(floor(smallesty))
    largesty = (y_origin/ratio)
    maxy = int(ceil(largesty))

    return miny, maxy


def draw_line(pointer, screen_x1, screen_y1, screen_x2, screen_y2):
    """
    Draw a line between two pixel coordinates (screen_x_1, screen_y_1) to (screen_x_2, screen_y_2)
    :param pointer: Turtle pointer to draw with
    :param screen_x1: The pixel x of line start
    :param screen_y1: The pixel y of line start
    :param screen_x2: The pixel x of line end
    :param screen_y2: The pixel y of line end
    :return: None (just draws in turtle)
    """
    pointer.up()
    pointer.goto(screen_x1,screen_y1)
    pointer.down()
    pointer.goto(screen_x2,screen_y2)
    pass


def draw_x_axis_tick(pointer, screen_x, screen_y):
    """
    Draw an x-axis tick for location (screen_x, screen_y)
    :param pointer: Turtle pointer to draw with
    :param screen_x: The pixel x of tick location on axis
    :param screen_y: The pixel y of tick location on axis
    :return: None (just draws in turtle)
    """
    pointer.color(AXIS_COLOR)
    pointer.up()
    pointer.goto(screen_x,screen_y-TICKSPACING)
    pointer.down()
    pointer.goto(screen_x,screen_y+TICKSPACING)


    
    pass


def draw_x_axis_label(pointer, screen_x, screen_y, label_text):
    """
    Draw an x-axis label for location (screen_x, screen_y), label is label_text
    :param pointer: Turtle pointer to draw with
    :param screen_x: The pixel x of tick location on axis
    :param screen_y: The pixel y of tick location on axis
    :param label_text: The string label to draw
    :return: None (just draws in turtle)
    """
    pointer.color(AXIS_COLOR)
    pointer.up()
    pointer.goto(screen_x,screen_y-LABELSPACING)
    pointer.down()
    pointer.write(label_text)
    pass


def draw_y_axis_tick(pointer, screen_x, screen_y):
    """
    Draw an y-axis tick for location (screen_x, screen_y)
    :param pointer: Turtle pointer to draw with
    :param screen_x: The pixel x of tick location on axis
    :param screen_y: The pixel y of tick location on axis
    :return: None (just draws in turtle)
    """
    pointer.color(AXIS_COLOR)
    pointer.up()
    pointer.goto(screen_x-TICKSPACING,screen_y)
    pointer.down()
    pointer.goto(screen_x+TICKSPACING,screen_y)
    pass


def draw_y_axis_label(pointer, screen_x, screen_y, label_text):
    """
    Draw an y-axis label for location (screen_x, screen_y), label is label_text
    :param pointer: Turtle pointer to draw with
    :param screen_x: The pixel x of tick location on axis
    :param screen_y: The pixel y of tick location on axis
    :param label_text: The string label to draw
    :return: None (just draws in turtle)
    """
    pointer.color(AXIS_COLOR)
    pointer.up()
    pointer.goto(screen_x-LABELSPACING,screen_y)
    pointer.down()
    pointer.write(label_text)
    pass


def draw_x_axis(pointer, x_origin, y_origin, ratio):
    """
    Draw an x-axis centred on given origin, with given ratio
    :param pointer: Turtle pointer to draw with
    :param x_origin: Pixel x origin of pixel coordinate system
    :param y_origin: Pixel y origin of pixel coordinate system
    :param ratio: Ratio of pixel coordinate system (each 1 in calculator is worth ratio amount of pixels)
    :return: None (just draws in turtle)
    """
    xvalueminimum,xvaluemaximum = calc_minmax_x(x_origin,ratio)

    screencordx1, screencordy1 = calc_to_screen_coord(xvalueminimum, 0, x_origin, y_origin, ratio)
    screencordx2,screencordy2 = calc_to_screen_coord(xvaluemaximum, 0, x_origin, y_origin, ratio)


    draw_line(pointer,screencordx1,screencordy1,screencordx2,screencordy2)

    for x in range(xvalueminimum,xvaluemaximum+SPACING,SPACING):
        screencordx1,screencordy1 = calc_to_screen_coord(xvalueminimum, 0, x_origin, y_origin, ratio)



        draw_x_axis_tick(pointer,screencordx1,screencordy1)

        draw_x_axis_label(pointer,screencordx1,screencordy1,xvalueminimum)

        xvalueminimum+=1







    pass


def draw_y_axis(pointer, x_origin, y_origin, ratio):
    """
    Draw an y-axis centred on given origin, with given ratio
    :param pointer: Turtle pointer to draw with
    :param x_origin: Pixel x origin of pixel coordinate system
    :param y_origin: Pixel y origin of pixel coordinate system
    :param ratio: Ratio of pixel coordinate system (each 1 in calculator is worth ratio amount of pixels)
    :return: None (just draws in turtle)
    """
    yvalueminimum, yvaluemaximum = calc_minmax_x(y_origin, ratio)

    screencordx1,screencordy1 = calc_to_screen_coord(0, yvalueminimum, x_origin, y_origin, ratio)
    screencordx2,screencordy2 = calc_to_screen_coord(0, yvaluemaximum, x_origin, y_origin, ratio)


    draw_line(pointer, screencordx1, screencordy1, screencordx2, screencordy2)

    for x in range(yvalueminimum, yvaluemaximum+SPACING,SPACING):
        screencordx1,screencordy1 = calc_to_screen_coord(0, yvalueminimum, x_origin, y_origin, ratio)


        draw_y_axis_tick(pointer, screencordx1, screencordy1)

        draw_y_axis_label(pointer, screencordx1, screencordy1, yvalueminimum)

        yvalueminimum += 1



    pass


def draw_expression(pointer, expr, colour, x_origin, y_origin, ratio):
    """
    Draw expression centred on given origin, with given ratio
    :param pointer: Turtle pointer to draw with
    :param expr: The string expression to draw
    :param colour: The colour to draw the expression
    :param x_origin: Pixel x origin of pixel coordinate system
    :param y_origin: Pixel y origin of pixel coordinate system
    :param ratio: Ratio of pixel coordinate system (each 1 in calculator is worth ratio amount of pixels)
    :return: None (just draws in turtle)
    """
    pointer.color(colour)
    delta = 0.2
    minimumx,maximumx = calc_minmax_x(x_origin, ratio)
    minimumy,maximumy = calc_minmax_x(y_origin, ratio)
    x = minimumx
    while x <= maximumx:
        x1 = x
        x2 = x + delta
        y1 = calc(expr,x1)
        y2 = calc(expr,x2)
        screencordx1, screencordy1 = calc_to_screen_coord(x1,y1, x_origin, y_origin, ratio)
        screencordx2, screencordy2 = calc_to_screen_coord(x2,y2, x_origin, y_origin, ratio)

        draw_line(pointer,screencordx1,screencordy1,screencordx2,screencordy2)

        x+= 0.2







    pass


# YOU SHOULD NOT NEED TO CHANGE ANYTHING BELOW THIS LINE UNLESS YOU ARE DOING THE BONUS


def calc(expr, x):
    """
    Return y for y = expr(x)
    Example if x = 10, and expr = x**2, then y = 10**2 = 100.
    :param expr: The string expression to evaluate where x is the only variable
    :param x: The value to evaluate the expression at
    :return: y = expr(x)
    """
    return eval(expr)


def setup():
    """
    Sets the window up in turtle
    :return: None
    """
    turtle.bgcolor(BACKGROUND_COLOR)
    turtle.setup(WIDTH, HEIGHT, 0, 0)
    screen = turtle.getscreen()
    screen.screensize(WIDTH, HEIGHT)
    screen.setworldcoordinates(0, 0, WIDTH, HEIGHT)
    screen.delay(delay=0)
    pointer = turtle
    pointer.hideturtle()
    pointer.speed(0)
    pointer.up()
    return pointer


def main():
    """
    Main loop of calculator
    Gets the pixel origin location in the window and a ratio
    Loops a prompt getting expressions from user and drawing them
    :return: None
    """
    # Setup
    pointer = setup()
    # turtle.tracer(0)
    # Get configuration
    x_origin, y_origin = eval(input("Enter pixel coordinates of chart origin (x,y): "))
    ratio = int(input("Enter ratio of pixels per step: "))
    # Draw axis
    pointer.color(AXIS_COLOR)
    draw_x_axis(pointer, x_origin, y_origin, ratio)
    draw_y_axis(pointer, x_origin, y_origin, ratio)
    # turtle.update()
    # Get expressions
    expr = input("Enter an arithmetic expression: ")
    equation_counter = 0
    while expr != "":
        # Get colour and draw expression
        colour = get_color(equation_counter)
        draw_expression(pointer, expr, colour, x_origin, y_origin, ratio)
        # turtle.update()
        expr = input("Enter an arithmetic expression: ")
        equation_counter += 1


main()
turtle.exitonclick()