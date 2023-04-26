+++
title = "Partycje"
date = "2023-04-26"
lastmod = "2023-04-26"
author = "Szymzal"
authorTwitter = "" #do not include @
cover = ""
tags = ["linux"]
keywords = ["linux"]
description = "Jak tworzyć partycje oraz je przeglądać"
showFullContent = false
readingTime = false
hideComments = false
color = "" #color from the theme settings
draft = false
+++
# Przeglądanie partycji

Do tego używana jest komenda `lsblk`.
Wyświetla ono wszytkie dyski podłączone do komputera wraz z CD i DVD.
Wyświetla wielkość, nazwę, typ oraz "mount point" (jeśli występuje).

# Formatowanie partycji

Do tego używana jest komenda `mkfs`. Komenda ta wymaga uprawnień root.
Użytkowanie komendy wygląda tak: `mkfs -t <system plików> <partycja>`, gdzie `<system plików>` zamieniamy na np. `ext4`, `ntfs`, `vfat`, itp., a `<partycja>` na ścieszkę do partycji, np. `/dev/sdb1`.

# Tworzenie partycji

Używa się to tego komendy `fdisk`. Komenda ta wymaga uprawnień root.
Wyświetlenie informacji o dyskach wykonuje się za pomocą komendy: `fdisk -l`.

Aby zacząć modyfikowanie dysku wpisujemy komendę: `fdisk <dysk>`, gdzie `<dysk>` zamieniamy na ścieszkę do dysku, np. `/dev/sdb`.
Wyświetli się miejsce do podania komendy. Możemy wpisać `m`, aby wyświetlić pomoc.

Używając polecenia `n`, możemy stworzyć partycję wybierając numer partycji, pierwszy sektor oraz ostatni sektor.
Kiedy zakończymy modyfikację dysku, możemy zapisać zmiany za pomocą polecenia `w`.

# Źródła
- https://phoenixnap.com/kb/linux-format-disk
- https://www.digitalocean.com/community/tutorials/create-a-partition-in-linux
