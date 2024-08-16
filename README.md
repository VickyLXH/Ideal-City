# **Ideal City**

## Description
<ul>
  <li>A game simulating city management for gamers who love business simulation games. 
Players are allowed to design their dream city. Possible features include:
    <ul>
      <li> Build city infrastructure(school, hospital, bank, residence, park etc.) to 
earn income and improve residents' satisfaction.</li>
      <li> Upgrade facilities or reclaim land to increase tax revenue.</li>
      <li> Check detail information of a facility, including building level, tax revenue etc.</li>
      <li> Remove a building from city. </li>
    </ul>
  </li><br>

  <li>Regardless of age and gender, this game is suitable for all ages. 
However, according to 2022 data statistics, over 46% of the casual
game players are between ages of 23-46; over 25% of the casual
game players are between ages of 16-22; in terms of gender, over 71%
of simulation games are female. In conclusion, this game is mainly 
for females aged between 16 and 46.</li><br>

  <li>There are two reasons choosing this project: 
    <ul> 
      <li>The first reason will be my interest in games. Casual games, including
simulation games is prospective as more people play games 
with fragmentary time in their daily life, and the target audience,
female gamers has increased in recent years. </li>
      <li>Another reason for choosing this project is my interest in economics in games. I studied
economics before and read some articles about economics in games.
It turns out that a lot of the games I have played have completely 
complex economic systems, which may have a huge impact on the balance of the game. For instance, 
many MMORPG games have trading systems in games. Although it might be impractical to build a complex 
and complete economic system in this game, at least it will be a good try.</li></ul></li>
     
</ul>

## User Stories
- As a user, I want to be able to build a new hospital on land.
- As a user, I want to be able to view the detail information of 
a restaurant I built.
- As a user, I want to be able to upgrade a level 1 house to level 2
when I have enough treasure.
- As a user, I want to be able to remove the park I built before.
- As a user, I want to be able to add the tax revenue to my treasure.
- As a user, I want to save the game at current state.
- As a user, I want to load the previous game I played.

# Instructions for Grader

- You can generate the first required action related to adding facilities to a land by click
Menu → Build → any button inside it.
- You can generate the second required action related to adding facilities income to income by
clicking the $ button in the upper right corner.
- You can locate my visual component in .\data
- You can save the state of my application by clicking Menu → Save
- You can reload the state of my application by clicking Menu → Load

# Phase 4: Task 2
- Thu Apr 13 11:11:28 PDT 2023
Revenue added!
- Thu Apr 13 11:11:30 PDT 2023
Fire department added!
- Thu Apr 13 11:11:36 PDT 2023
Residence upgraded!
- Thu Apr 13 11:11:43 PDT 2023
Restaurant removed!
- Thu Apr 13 11:16:13 PDT 2023
  Residence displayed!

# Phase 4: Task 3
When I first started designing this game, I first thought that 
I should have different types of houses, land and a bank account for player.
Since houses could all have same fields, but different values, using interface
could make the code more concise. So I create the Facility Interface;
land is simply a list of different types of houses, which is Facility. After doing
all of these I realized I should make a new class called Player to combine 
all of these classes together.

If I had more time to work on the project, I will first complete my revenue system,
as I want to add revenue to account every 24 hours instead of getting revenue 
any time. After that, I would like to refactor my Facility upgrade system,
extract all upgrade to a new class to make the upgrade system of all types of 
house consistent with each other.