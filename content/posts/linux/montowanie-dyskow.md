+++
title = "Montowanie dysków"
date = "2023-04-25"
lastmod = "2023-04-27"
author = "Szymzal"
authorTwitter = "" #do not include @
cover = ""
tags = ["linux", "disks", "filesystem", "mounting"]
keywords = ["linux", "disks", "filesystem", "mounting"]
description = "Jak działa montowanie dysków"
showFullContent = false
readingTime = false
hideComments = false
color = "" #color from the theme settings
draft = false
+++
# Teoria

System plików to ogólnie mówiąc sposób organizowania plików na dysku i zaczyna się od `/`.

W linuxie nie ma zbędnego łączenia folderu z fizycznym urządzeniem (np. dyskiem). Gdzie na przykład w Windowsie każdy dysk zaczyna się literą (np. `D:\`)
Jednak, każdy dysk ma w sobie zazwyczaj więcej niż jedny system plików. Aby mieć możliwość dostać się do nich przypisuje się folder do innego systemu plików.
Taki folder, który ma w sobie inny system plików nazywany jest *mount point*. "Zawiera" ono ten drugi system plików.

Przykładowo DVD możesz zamontować w `/dvd`, w którym możesz obejrzeć zawartość tej płyty.

# Urządzenia

Wszystkie urządzenia mają nazwy i są przechowywane w folderze `/dev`.
Nie ma jakiegoś ogólnie standardu jak nazywać urządzenia w folderze `/dev`.
Z takich popularniejszych urządzeń, które można znaleźć to:
- `dvd`
- `cdrom`
- `tty`
- `stdin`
- `stdout`
- `stderr`

# Nazywanie dysków

Przykładem jakiegoś większego zorganizowania są dyski, dyskietki.

Gdzie "x" to numer dysku w formacie alfabetu. (a -> pierwszy dysk, b -> drugi dysk itp.)

### hdx

Są to dyski, które używają kontrolera IDE. 

### sdx

Są to dyski, które używają kontrolera SCSI lub SATA.

### fdx

Są to dyskietki.

## Partycje 

Partycje są oznaczane liczbą na koniec nazwy urządzenia. 
Więc przykładowo jak mamy 2 dysk SSD, który ma 2 partycje to można je znaleźć pod nazwami:
- `sdb1` -> pierwsza partycja
- `sdb2` -> druga partycja

# Montowanie płyty DVD

Oczywiście są to tego potrzebne uprawnienia roota.

Kroki:
1. Należy stworzyć folder do którego będziemy montować system plików.
2. Używając polecenia `mount` bez argumentów możemy zobaczyć jakie systemy plików zostały już zamontowane w naszym systemie.
3. Używając polecenia `mount <urządzenie> <mount_point>`, gdzie `<urządzenie>` zamieniamy na ścieszkę do urządzenia (np. `/dev/cdrom`), a `<mount_point>` na ścieszkę gdzie chcemy zamontować system plików (np. `/cdrom`). Więc komenda wyglądała by tak: `mount /dev/cdrom /cdrom`
4. W tym momencie możemy zobaczyć co jest na płycie wchodząc do folderu `/cdrom`

Aby odmontować system plików:
1. Użyć polecenia `umount <mount_point>`, gdzie `<mount_point>` zamieniamy na ścieszkę gdzie znajduje się system plików, który chcemy odmontować. Więc polecenie wyglądało by tak: `umount /cdrom`.

# Automatyzacja

Montowanie dysków utrzymuje się tylko przez czas kiedy komputer jest załączony.
Jest możliwość zautomatyzowania tego w pliku `/etc/fstab`.

Aby zapisać należy podać parę danych:
- file system (system plików) -> ścieszka do urządzenia (np. `/dev/cdrom`) lub UUID urządzenia, które można odczytać używając komendy `blkid` (wyglądało by na przykład tak: `UUID=7cfe034c-1633-4c02-a7e3-742515367b35`).
- mount point (punkt montowania) -> ścieszka do folderu do którego ma się montować system plików (np. `/cdrom`). Można użyć tutaj `none` jeśli nie ma żadnego puntku montażu lub `auto` na automatycznie wykrywanie.
- type (typ systemu plików) -> jaki typ plików powinien być używany (np. `ntfs`, `ext4`, `iso9660`)
- options (opcje) -> [lista opcji](#opcje)
- dump -> W większości wypadków wpisuje się tu `0`.
- pass -> kolejność w której spawdzać systemy plików podczas bootowania komputera. Przykładowo: 1 -> będzie sprawdzany jako pierwszy, 2 -> jako drugi, itp. Wyjątkiem jest 0, który oznacza, żeby tego dysku nie sprawdzać przy procesie bootowania.

Aby sprawdzić czy automatyzacja działa poprawnie można albo uruchomić ponownie komputer lub wykonać polecenie `mount -a`.

### Opcje

Lista opcji:
- `async`, `sync` -> Tryb w jaki ma pracować I/O.
- `ro`, `rw` -> Ustaw system plików jako tylko do oczytu (read-only `ro`) lub do oczytu i zapisu (read-write `rw`).
- `auto`, `noauto` -> Czy system plików należy automatycznie montować przy uruchamianiu komputera lub przy użyciu komendy `mount -a`.
- `dev`, `nodev` -> Czytaj lub ignoruj pliku urządzeń znajdujące się w systemie plików.
- `user`, `nouser` -> Pozwól na zwykłych użytkowników aby mogli montować ten system plików. `nouser` oznacza, że tylko root może montować ten system plików. A `user` pozwala, żeby inni użytkownicy mogli montować ten sytem plików.
- `exec`, `noexec` -> Czy będzie można uruchamiać pliki w tym systemie plików?
- `suid`, `nosuid` -> Czy "suid" bit ma mieć jakieś znaczenie?
- `defaults` -> Domyślne ustawienia. Znaczy to samo co: `rw,dev,suid,exec,auto,nouser,async`.

Opcje można łączyć za pomocą przecinka `,`. Np. `rw,async,exec`. Dotyczy to także `defaults`.

#### Po co opcje `noauto`, `user`?

Jak można było zauważyć wcześniej. Używanie pełnej komendy `mount` wymaga roota.
Więc jak korzystać z np. `noauto` lub `user`?

Wystarczy wtedy podać tylko mount point, np. `mount /cdrom`.

# RAID

Przy RAID-ie nie montuje się pojedynczej partycji. W takiej sytuacji montuje się tzw. "plik RAID-u" (np. /dev/md2).

Więcej informacji jak to zrobić: [RAID](/posts/linux/raid)

# Źródła
- https://www.debian.org/doc/manuals/debian-tutorial/ch-disks.html
