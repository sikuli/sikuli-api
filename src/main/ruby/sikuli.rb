require 'java'
require 'sikuli-api.jar'

java_import 'org.sikuli.api.Mouse'
java_import 'org.sikuli.api.ScreenRegion'

RScreenRegion = org.sikuli.api.ScreenRegion
class RScreenRegion

  def click(targetString, params = {})
    loc = getScreenLocation(targetString, params)
    org.sikuli.api.Mouse.click(loc)
  end

  def leftClick(targetString, params = {})
    loc = getScreenLocation(targetString, params)
    org.sikuli.api.Mouse.leftClick(loc)
  end  

  def doubleClick(targetString, params = {})
    loc = getScreenLocation(targetString, params)
    org.sikuli.api.Mouse.rightClick(loc)
  end  

  def find(targetString, params = {})
    target = createTargetFromString(targetString, params)
    @s.find(target)
  end
  
  def findAll(targetString, params = {})
    target = createTargetFromString(targetString, params)
    @s.findAll(target)
  end
  
  
  alias_method :center, :getCenter
  alias_method :old_wait, :wait
    
  def wait(targetString, timeout, params = {})
    target = createTargetFromString(targetString, params)
    old_wait(target,timeout)
  end  
    
  private

  def resolve_url(targetString)
    begin
      java.net.URL.new(targetString)  
    rescue
      return resolve_fileurl(targetString)
    end
  end

  def resolve_fileurl(targetString)  
    if File.exists?(Dir.pwd + "/" + targetString) # search relative path
      return java.net.URL.new("file://" + Dir.pwd + "/" + targetString)
    elsif File.exists? targetString # search absolute path
      return java.net.URL.new("file://" + targetString)
    else
      return nil
    end
  end
  
  def createTargetFromString(targetString, params)
    image_url = resolve_url(targetString)
    target = org.sikuli.api.ImageTarget.new(image_url)
    apply_params(target, params)
    target
  end

  def getScreenLocation(targetString, params)
    target = createTargetFromString(targetString, params)
    r = @s.find(target)
    r.getCenter()
  end
  
  def apply_params(target, params = {})
    if params[:limit]
      target.setLimit(params[:limit])
    end
    if params[:minScore]
      target.setMinScore(params[:minScore])
    end
    if params[:ordering]
      case params[:ordering]
        when :top_down
          target.setOrdering(org.sikuli.api.Target::Ordering::TOP_DOWN)
        when :bottom_up
          target.setOrdering(org.sikuli.api.Target::Ordering::BOTTOM_UP)
        when :left_right
          target.setOrdering(org.sikuli.api.Target::Ordering::LEFT_RIGHT)        
        when :right_left
          target.setOrdering(org.sikuli.api.Target::Ordering::RIGHT_LEFT)                
        end    
    end    
  end

end

require 'rbconfig'
def browse(url)
  include Config
  # Tip 2 on http://ruby.dzone.com/articles/10-hot-tips-jruby
  case CONFIG['host_os']
  when /darwin/i
    # Mac
    `open http://code.google.com/`
  when /mswin|windows/i
    # Windows
    `start http://code.google.com/`
  when /linux/i
    # Linux
  when /sunos|solaris/i
    # Solaris
  else
    # Whatever
  end  
end