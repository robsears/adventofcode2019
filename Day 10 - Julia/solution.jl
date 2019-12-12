using LinearAlgebra

space  = []

# Read the input file and process it into coordinates. Push into the space array
io = open("input", "r")
y = 1
for (index, line) in enumerate(split(read(io, String), "\n"))
    if cmp("$line", "\n") > 0
        value = chomp(line)
        for x = 1:length("$value")
            if value[x] == '#'
                push!(space, [x, y])
            end
        end
        global y += 1
    end
end
close(io)

# Calculate the theta between two asteroids
#
# Returns the theta between asteroid and asteroid_2 (0 <= theta <= 2pi)
function vector(asteroid, asteroid_2)
    xdiff = asteroid_2[1]-asteroid[1]
    ydiff = asteroid_2[2]-asteroid[2]
    dir = atan(ydiff/xdiff)
    q = (xdiff >= 0) ? ((ydiff >= 0) ? 0 : 2*pi) : pi
    return dir + q
end

# Calculate the distance between two asteroids
#
# Returns a Float representing the distance between the asteroids
function distance(asteroid, asteroid_2)
    sqrt(abs(asteroid[1]-asteroid_2[1])^2+abs(asteroid[2]-asteroid_2[2])^2)
end

# Determine whether or not asteroid_3 sits between asteroid and asteroid_2
#
# Returns a Boolean indicating whether asteroid_3 is blocking LoS beteen
# asteroid and asteroid_2.
function blocker(asteroid, asteroid_2, asteroid_3)
    if vector(asteroid, asteroid_2) == vector(asteroid, asteroid_3)
        return distance(asteroid, asteroid_2) > distance(asteroid, asteroid_3)
    end
    return false
end

# Examine the map of all asteroids around a given asteroid and calculate which
# asteroids are within the line of site.
#
# Returns an Int of the count of asteroids within LoS.
function linesOfSight(asteroid)
    global space
    directLOS = 0
    colinear = 0
    for asteroid_2 in space # Asteroid 2 is the one we want to compare against
        blockers = 0
        if asteroid != asteroid_2
            for asteroid_3 in space # Asteroid 3 is a potential LoS blocker
                if asteroid != asteroid_3 && asteroid_2 != asteroid_3
                    if round(det([asteroid[1] asteroid[2] 1; asteroid_2[1] asteroid_2[2] 1; asteroid_3[1] asteroid_3[2] 1])) == 0
                        if blocker(asteroid, asteroid_2, asteroid_3)
                            blockers += 1
                        end
                    end
                end
            end
            directLOS += (blockers == 0) ? 1 : 0
        end
    end
    return directLOS
end

# Arrange the keys of a dictionary so they are in clockwise order from the
# 3/2 pi axis ("up" in this case).
#
# Returns an Array of sorted keys
function orientKeys(dict)
    sorted_keys = []
    dict_keys   = sort(collect(keys(dict)), rev=false)
    start_point = findfirst(isequal(3*pi/2), dict_keys)
    for i = start_point:length(dict_keys)
        push!(sorted_keys, dict_keys[i])
    end
    for i = 1:start_point-1
        push!(sorted_keys, dict_keys[i])
    end
    return sorted_keys
end

# Build a dictionary of asteroids around an asteroid, keyed to the vector of a
# common asteroid. Each key has an array of asteroids which are colinear with
# the common asteroid.
#
# Returns a Dict.
function buildDict(asteroid)
    dict = Dict()
    for asteroid_2 in space
        if asteroid != asteroid_2
            vec = vector(asteroid, asteroid_2)
            if !haskey(dict, vec)
                dict[vec] = [asteroid_2]
            else
                asteroids = dict[vec]
                push!(asteroids, asteroid_2)
                dict[vec] = asteroids
            end
        end
    end
    return dict
end

# Calculate the closest asteroid in an array of asteroids, to a given asteroid.
#
# Returns an Array representing the position of the closest asteroid.
function closestAsteroid(asteroid, asteroids)
    closest = []
    dist = 999999
    for asteroid_2 in asteroids
        if distance(asteroid, asteroid_2) < dist
            dist = distance(asteroid, asteroid_2)
            closest = asteroid_2
        end
    end
    return closest
end

# Calculate Part 1.
#
# Return an Array representing the location of the best asteroid to settle
function part_1_solution()
    best_asteroid = []
    max_detected = 0
    for (index, asteroid) in enumerate(space)
        ans = linesOfSight(asteroid)
        if max_detected < ans
            max_detected = ans
            best_asteroid = asteroid
        end
    end
    best_asteroid_zero = [best_asteroid[1]-1 best_asteroid[2]-1]
    println("Part 1: Best is $best_asteroid_zero with $max_detected.")
    return best_asteroid
end

# Calculate the solution for Part 2.
function part_2_solution(best_asteroid)
    blasted = 0
    asteroid = best_asteroid
    blasteroid = []
    dict = buildDict(asteroid)
    rotation_keys = orientKeys(dict)
    while blasted < 200
        for key in rotation_keys
            asteroids = dict[key]
            if length(asteroids) > 0
                blasteroid = closestAsteroid(asteroid, asteroids)
                blasted += 1
                if blasted == 200
                    break
                end
                if length(asteroids) > 0
                    dict[key] = asteroids
                else
                    delete!(dict, key)
                end
            end
            if length(keys(dict)) == 0
                break
            end
        end
        if length(keys(dict)) == 0
            break
        end
    end
    println("Part 2: $((blasteroid[1]-1)*100+blasteroid[2]-1)")
end

best_asteroid = part_1_solution()
part_2_solution(best_asteroid)
