+++
title = "RAID"
date = "2023-04-27"
lastmod = "2023-04-27"
author = "Szymzal"
authorTwitter = "" #do not include @
cover = ""
tags = ["linux", "RAID", "disks"]
keywords = ["linux", "RAID", "disks"]
description = "Jak tworzyć RAID-y w linuxie"
showFullContent = false
readingTime = false
hideComments = false
color = "" #color from the theme settings
+++
# Program

Wykorzystuje się to tego program `mdadm`. 
Przykładowo w Debianie nie ma zainstalowanego fabrycznie tego programu.
Dodatkowo jeśli nie można go znaleźć (Debian) należy dodać repozytorium `bullseye` do apt.

Program ten tworzy urządzenia *MD* (Multiple Devices), czyli *Software RAID*.
Jest to co innego niż stworzenie RAID-a w BIOS/UEFI naszej płyty głównej.

W wersji 4.1-11 programu obsługuje:
- RAID 0
- RAID 1
- RAID 4
- RAID 5
- RAID 6
- RAID 10
- MULTIPATH
- FAULTY
- CONTAINER

Z czego 3 ostatnie to nie tak naprawdę mechanizmy *Software RAID*.

# Użytkowanie

Obsługa komendy wygląda tak:
`mdadm <tryb> <urządzenie RAID> <opcje> <urządzenia>`, gdzie:
- `<tryb>` -> [Tryby](#tryby)
- `<urządzenie RAID>` -> Plik urządzenia zapisywany w folderze `/dev`. Nazywany jest najczęściej `mdx`, gdzie "x" jest następnym numerem, który jest dostępny (np. `/dev/md2`).
- `<opcje>` -> [Opcje](#opcje)
- `<urządzenia>` -> Urządzenia, na które polecenie te będzie miało efekt

### Tryby

- `-A`, `--assemble` -> Stwórz za pomocą już wcześniej stworzonych części.
- `-B`, `--build` -> Stwórz bez metadanych. (Odradza się używania tego)
- `-C`, `--create` -> Stwórz z metadanymi.
- `-F`, `--follow`, `--monitor` -> Monitoruj urządzenia *MD* i wykonuj jakieś działanie kiedy zmieni się status.
- `-G`, `--grow` -> Dołóż lub usuń w istniejącej już *MD* lub zmień poziom *MD*. (Dokładanie tylko dla RAID-y: 0, 1, 4, 5, 6. Zmienianie poziomu RAID-a pomiędzy 0, 4, 5, 6 oraz pomiędzy 0 i 10).
- `--add`, `--remove`, `--replace` -> Tryb `Manage`, dodaje, usuwa, zamienia urządzenia z *MD*.

### Opcje

- `-v`, `--verbose` -> Otrzymuj więcej informacji co się dzieje.
- `-q`, `--quiet` -> Nie pokazuj ogólnych informacji. Jedynie te bardzo ważne.

Create, Build, Grow:
- `-n`, `--raid-devices=` -> Ile urządzeń będzie miał efekt to polecenie.
- `-x`, `--spare-devices=` -> Urządzenia typu "spare". Zobacz: `--replace`.
- `-l`, `--level` -> Poziom *MD*.
- `-o`, `--readonly` -> Zrób tylko do odczytu.
- `-a`, `--add` -> Dodaj urządzenia w urządzeniu *MD*

Manage:
- `--add-spare` -> Dodaj urządzenie typu "spare". Zobacz: `--replace`.
- `-r`, `--remove` -> Usuń urządzenie z *MD*. Urządzenie te musi być nieaktywne lub niesprawne.
- `-f`, `--fail` -> Oznacz urządzenie jako niesprawne.
- `--replace` -> Oznacz urządzenie, że potrzebuje wymiany. Jak pojawi się urządzenie typu "spare", urządzenia zostaną zamienione.
- `--with` -> Korzystać razem z `--replace`. Wybierz preferencje, których urządzeń użyć zamiast urządzenia do wymiany. Urządzenia te muszą być już zamontowane w *MD*.

# Przykład tworzenia RAID 1

Mamy 2 dyski z jedną partycją.
Dyski nazwane są: `sdb`, `sdc`.
Więc komenda wyglądała by tak:

```
mdadm --create /dev/md2 --level=1 --raid-devices=2 /dev/sdb1 /dev/sdc1
```

Warto zauważyć, że dodaje się partycje a nie urządzenia!

# Automatyzacja

Program automatycznie będzie skanował wszystkie partycje (/proc/partitions) i będzie szukał informacji o urządzeniach *MD*.

Wszystkie urządzenia *MD* można zobaczyć w */proc/mdstat*.
