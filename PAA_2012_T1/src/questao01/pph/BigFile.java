package questao01.pph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;

public class BigFile implements Iterable<String> {
  private BufferedReader _reader;

  public BigFile(String filePath) throws Exception {
    _reader = new BufferedReader(new FileReader(filePath));
  }

  public void Close() {
    try {
      _reader.close();
    }
    catch (Exception ex) {
    }
  }

  @Override
  public Iterator<String> iterator() {
    return new FileIterator();
  }

  private class FileIterator implements Iterator<String> {
    private String _currentLine;

    @Override
    public boolean hasNext() {
      try {
        _currentLine = _reader.readLine();
        //        String temp = input.nextLine();
        //        String[] parse = temp.split(" ");
      }
      catch (Exception ex) {
        _currentLine = null;
        ex.printStackTrace();
      }

      return _currentLine != null;
    }

    @Override
    public String next() {
      return _currentLine;
    }

    @Override
    public void remove() {
    }
  }
}